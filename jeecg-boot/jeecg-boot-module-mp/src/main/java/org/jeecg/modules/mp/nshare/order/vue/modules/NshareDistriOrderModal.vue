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
            <a-form-item label="店铺" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['shopId']" :trigger-change="true" dictCode="nshare_distri_shop,shop_name,id" placeholder="请选择店铺"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'orderNo', validatorRules.orderNo]" placeholder="请输入订单号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="订单时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择订单时间" v-decorator="[ 'orderDate', validatorRules.orderDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="订单金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'orderAmount', validatorRules.orderAmount]" placeholder="请输入订单金额"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="取货点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['stationId']" :trigger-change="true" dictCode="" placeholder="请选择取货点"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="收货人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'pickUser', validatorRules.pickUser]" placeholder="请输入收货人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="收货人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'pickPhone', validatorRules.pickPhone]" placeholder="请输入收货人电话"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="取货码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'pickCode', validatorRules.pickCode]" placeholder="请输入取货码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="订单状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'orderStatus', validatorRules.orderStatus]" placeholder="请输入订单状态"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="确认时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择确认时间" v-decorator="[ 'confirmTime', validatorRules.confirmTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="取货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择取货时间" v-decorator="[ 'pickTime', validatorRules.pickTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="社区分享配送订单商品" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="nshareDistriOrderGoodsTable.loading"
            :columns="nshareDistriOrderGoodsTable.columns"
            :dataSource="nshareDistriOrderGoodsTable.dataSource"
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
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'NshareDistriOrderModal',
    mixins: [JEditableTableMixin],
    components: {
      JDate,
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
          shopId:{},
          orderNo:{},
          orderDate:{},
          orderAmount:{},
          stationId:{},
          pickUser:{},
          pickPhone:{},
          pickCode:{},
          orderStatus:{},
          confirmTime:{},
          pickTime:{},
        },
        refKeys: ['nshareDistriOrderGoods', ],
        tableKeys:['nshareDistriOrderGoods', ],
        activeKey: 'nshareDistriOrderGoods',
        // 社区分享配送订单商品
        nshareDistriOrderGoodsTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '订单',
              key: 'orderId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '商品',
              key: 'goodsId',
              type: FormTypes.select,
              dictCode:"nshare_distri_shop_goods,goods_name,id",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '数量',
              key: 'orderNum',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '单位',
              key: 'saleUnit',
              type: FormTypes.select,
              dictCode:"mp_ns_goods_unit",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '购买单价',
              key: 'salePrice',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/nshare.order/nshareDistriOrder/add",
          edit: "/nshare.order/nshareDistriOrder/edit",
          nshareDistriOrderGoods: {
            list: '/nshare.order/nshareDistriOrder/queryNshareDistriOrderGoodsByMainId'
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
        let fieldval = pick(this.model,'shopId','orderNo','orderDate','orderAmount','stationId','pickUser','pickPhone','pickCode','orderStatus','confirmTime','pickTime')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.nshareDistriOrderGoods.list, params, this.nshareDistriOrderGoodsTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          nshareDistriOrderGoodsList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'shopId','orderNo','orderDate','orderAmount','stationId','pickUser','pickPhone','pickCode','orderStatus','confirmTime','pickTime'))
     },

    }
  }
</script>

<style scoped>
</style>