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
            <a-form-item label="店铺码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'shopCode', validatorRules.shopCode]" placeholder="请输入店铺码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="店铺名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'shopName', validatorRules.shopName]" placeholder="请输入店铺名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="店铺描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'shopDesc', validatorRules.shopDesc]" placeholder="请输入店铺描述"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="店铺地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入店铺地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="店铺级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'shopLevel', validatorRules.shopLevel]" placeholder="请输入店铺级别"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="图片路径" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'picPath', validatorRules.picPath]" placeholder="请输入图片路径"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="店主" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['ownerId']" :trigger-change="true" dictCode="nshare_user,real_name,id" placeholder="请选择店主"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="店主电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'ownerPhone', validatorRules.ownerPhone]" placeholder="请输入店主电话"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="授权认证码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'authCode', validatorRules.authCode]" placeholder="请输入授权认证码"></a-input>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="社区分享配送店铺管理员" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="nshareDistriShopAdminTable.loading"
            :columns="nshareDistriShopAdminTable.columns"
            :dataSource="nshareDistriShopAdminTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
        <a-tab-pane tab="社区分享店铺配送点" :key="refKeys[1]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[1]"
            :loading="nshareDistriShopStationTable.loading"
            :columns="nshareDistriShopStationTable.columns"
            :dataSource="nshareDistriShopStationTable.dataSource"
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
    name: 'NshareDistriShopModal',
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
          shopCode:{},
          shopName:{},
          shopDesc:{},
          address:{},
          shopLevel:{},
          picPath:{},
          ownerId:{},
          ownerPhone:{},
          authCode:{},
        },
        refKeys: ['nshareDistriShopAdmin', 'nshareDistriShopStation', ],
        tableKeys:['nshareDistriShopAdmin', 'nshareDistriShopStation', ],
        activeKey: 'nshareDistriShopAdmin',
        // 社区分享配送店铺管理员
        nshareDistriShopAdminTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '店铺',
              key: 'shopId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '用户',
              key: 'userId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '管理类型',
              key: 'adminType',
              type: FormTypes.select,
              dictCode:"mp_ns_shop_admin_type",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        // 社区分享店铺配送点
        nshareDistriShopStationTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '店铺',
              key: 'shopId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '配送站',
              key: 'stationName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '地址',
              key: 'stationAddr',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/nshare.shop/nshareDistriShop/add",
          edit: "/nshare.shop/nshareDistriShop/edit",
          nshareDistriShopAdmin: {
            list: '/nshare.shop/nshareDistriShop/queryNshareDistriShopAdminByMainId'
          },
          nshareDistriShopStation: {
            list: '/nshare.shop/nshareDistriShop/queryNshareDistriShopStationByMainId'
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
        let fieldval = pick(this.model,'shopCode','shopName','shopDesc','address','shopLevel','picPath','ownerId','ownerPhone','authCode')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.nshareDistriShopAdmin.list, params, this.nshareDistriShopAdminTable)
          this.requestSubTableData(this.url.nshareDistriShopStation.list, params, this.nshareDistriShopStationTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          nshareDistriShopAdminList: allValues.tablesValue[0].values,
          nshareDistriShopStationList: allValues.tablesValue[1].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'shopCode','shopName','shopDesc','address','shopLevel','picPath','ownerId','ownerPhone','authCode'))
     },

    }
  }
</script>

<style scoped>
</style>