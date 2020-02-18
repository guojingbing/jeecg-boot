<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'title', validatorRules.title]" placeholder="请输入标题"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="作者" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag v-decorator="['authorId']" dict="tl_kb_author,author_name,id" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['formId']" :trigger-change="true" dictCode="mp_tl_poe_form" placeholder="请选择形式"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-editor v-decorator="['content',{trigger:'input'}]"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="释义" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-editor v-decorator="['poeExplain',{trigger:'input'}]"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="古诗词赏析评论" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="tlKbPoetryCommentTable.loading"
            :columns="tlKbPoetryCommentTable.columns"
            :dataSource="tlKbPoetryCommentTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
        <a-tab-pane tab="古诗词标签" :key="refKeys[1]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[1]"
            :loading="tlKbPoetryTagTable.loading"
            :columns="tlKbPoetryTagTable.columns"
            :dataSource="tlKbPoetryTagTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JEditor from '@/components/jeecg/JEditor'

  export default {
    name: 'TlKbPoetryModal',
    mixins: [JEditableTableMixin],
    components: {
      JDictSelectTag,
      JSearchSelectTag,
      JEditor,
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          title:{},
          authorId:{},
          formId:{},
          content:{},
          poeExplain:{},
        },
        refKeys: ['tlKbPoetryComment', 'tlKbPoetryTag', ],
        tableKeys:['tlKbPoetryComment', 'tlKbPoetryTag', ],
        activeKey: 'tlKbPoetryComment',
        // 古诗词赏析评论
        tlKbPoetryCommentTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '评论内容',
              key: 'comment',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        // 古诗词标签
        tlKbPoetryTagTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '诗词主键',
              key: 'poeId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '标签主键',
              key: 'tagId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/org.jeecg.modules.bing.mp.tlearn/tlKbPoetry/add",
          edit: "/org.jeecg.modules.bing.mp.tlearn/tlKbPoetry/edit",
          tlKbPoetryComment: {
            list: '/org.jeecg.modules.bing.mp.tlearn/tlKbPoetry/queryTlKbPoetryCommentByMainId'
          },
          tlKbPoetryTag: {
            list: '/org.jeecg.modules.bing.mp.tlearn/tlKbPoetry/queryTlKbPoetryTagByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'title','authorId','formId','content','poeExplain')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.tlKbPoetryComment.list, params, this.tlKbPoetryCommentTable)
          this.requestSubTableData(this.url.tlKbPoetryTag.list, params, this.tlKbPoetryTagTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          tlKbPoetryCommentList: allValues.tablesValue[0].values,
          tlKbPoetryTagList: allValues.tablesValue[1].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'title','authorId','formId','content','poeExplain'))
     },

    }
  }
</script>

<style scoped>
</style>