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
            <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'bannerDesc', validatorRules.bannerDesc]" placeholder="请输入描述"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="位置代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'locCode', validatorRules.locCode]" placeholder="请输入位置代码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="是否使用" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['isUse']" :trigger-change="true" dictCode="dict_item_status" placeholder="请选择是否使用"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="轮播条目" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="tlBannerItemTable.loading"
            :columns="tlBannerItemTable.columns"
            :dataSource="tlBannerItemTable.dataSource"
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

  export default {
    name: 'TlBannerModal',
    mixins: [JEditableTableMixin],
    components: {
      JDictSelectTag,
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
          bannerDesc:{},
          locCode:{},
          isUse: { rules: [{ required: true, message: '请输入是否使用!' }] },
        },
        refKeys: ['tlBannerItem', ],
        tableKeys:['tlBannerItem', ],
        activeKey: 'tlBannerItem',
        // 轮播条目
        tlBannerItemTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '轮播主键',
              key: 'bannerId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '标题',
              key: 'title',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '对应url',
              key: 'url',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '条目描述',
              key: 'itemDesc',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '轮播图片',
              key: 'pic',
              type: FormTypes.image,
              token:true,
              responseName:"message",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/banner/tlBanner/add",
          edit: "/banner/tlBanner/edit",
          tlBannerItem: {
            list: '/banner/tlBanner/queryTlBannerItemByMainId'
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
        let fieldval = pick(this.model,'title','bannerDesc','locCode','isUseString')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.tlBannerItem.list, params, this.tlBannerItemTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          tlBannerItemList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'title','bannerDesc','locCode','isUseString'))
     },

    }
  }
</script>

<style scoped>
</style>