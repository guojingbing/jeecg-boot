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
            <a-form-item label="昵称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'nickName', validatorRules.nickName]" placeholder="请输入昵称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'gender', validatorRules.gender]" placeholder="请输入性别" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="城市" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'city', validatorRules.city]" placeholder="请输入城市"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="省份" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'province', validatorRules.province]" placeholder="请输入省份"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="国家" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'country', validatorRules.country]" placeholder="请输入国家"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="头像" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'avatarUrl', validatorRules.avatarUrl]" placeholder="请输入头像"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入手机号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'level', validatorRules.level]" placeholder="请输入级别" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="子级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'levelSub', validatorRules.levelSub]" placeholder="请输入子级别" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="openid" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'openid', validatorRules.openid]" placeholder="请输入openid"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="unionid" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'unionid', validatorRules.unionid]" placeholder="请输入unionid"></a-input>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="用户收藏的诗词" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="tlUserSubPoetryTable.loading"
            :columns="tlUserSubPoetryTable.columns"
            :dataSource="tlUserSubPoetryTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
        <a-tab-pane tab="用户收藏的成语" :key="refKeys[1]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[1]"
            :loading="tlUserSubIdiomTable.loading"
            :columns="tlUserSubIdiomTable.columns"
            :dataSource="tlUserSubIdiomTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
        <a-tab-pane tab="用户订阅的诗词分类" :key="refKeys[2]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[2]"
            :loading="tlUserSubTagTable.loading"
            :columns="tlUserSubTagTable.columns"
            :dataSource="tlUserSubTagTable.dataSource"
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

  export default {
    name: 'TlUserModal',
    mixins: [JEditableTableMixin],
    components: {
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
          nickName:{},
          gender:{},
          city:{},
          province:{},
          country:{},
          avatarUrl:{},
          phone:{},
          level:{},
          levelSub:{},
          openid:{},
          unionid:{},
        },
        refKeys: ['tlUserSubPoetry', 'tlUserSubIdiom', 'tlUserSubTag', ],
        tableKeys:['tlUserSubPoetry', 'tlUserSubIdiom', 'tlUserSubTag', ],
        activeKey: 'tlUserSubPoetry',
        // 用户收藏的诗词
        tlUserSubPoetryTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '用户编号',
              key: 'userId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '诗词编号',
              key: 'poetryId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        // 用户收藏的成语
        tlUserSubIdiomTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '用户编号',
              key: 'userId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '成员编号',
              key: 'idiomId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        // 用户订阅的诗词分类
        tlUserSubTagTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '用户编号',
              key: 'userId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '订阅分类',
              key: 'tag',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/user/tlUser/add",
          edit: "/user/tlUser/edit",
          tlUserSubPoetry: {
            list: '/user/tlUser/queryTlUserSubPoetryByMainId'
          },
          tlUserSubIdiom: {
            list: '/user/tlUser/queryTlUserSubIdiomByMainId'
          },
          tlUserSubTag: {
            list: '/user/tlUser/queryTlUserSubTagByMainId'
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
        let fieldval = pick(this.model,'nickName','gender','city','province','country','avatarUrl','phone','level','levelSub','openid','unionid')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.tlUserSubPoetry.list, params, this.tlUserSubPoetryTable)
          this.requestSubTableData(this.url.tlUserSubIdiom.list, params, this.tlUserSubIdiomTable)
          this.requestSubTableData(this.url.tlUserSubTag.list, params, this.tlUserSubTagTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          tlUserSubPoetryList: allValues.tablesValue[0].values,
          tlUserSubIdiomList: allValues.tablesValue[1].values,
          tlUserSubTagList: allValues.tablesValue[2].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'nickName','gender','city','province','country','avatarUrl','phone','level','levelSub','openid','unionid'))
     },

    }
  }
</script>

<style scoped>
</style>