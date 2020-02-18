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
            <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'goodsName', validatorRules.goodsName]" placeholder="请输入商品名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="商品描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'goodsDesc', validatorRules.goodsDesc]" placeholder="请输入商品描述"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="店铺" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['shopId']" :trigger-change="true" dictCode="nshare_distri_shop,shop_name,id" placeholder="请选择店铺"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="社区分享店铺配送商品每日信息" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="nshareDistriShopGoodsDailyTable.loading"
            :columns="nshareDistriShopGoodsDailyTable.columns"
            :dataSource="nshareDistriShopGoodsDailyTable.dataSource"
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
    name: 'NshareDistriShopGoodsModal',
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
          goodsName:{},
          goodsDesc:{},
          shopId:{},
        },
        refKeys: ['nshareDistriShopGoodsDaily', ],
        tableKeys:['nshareDistriShopGoodsDaily', ],
        activeKey: 'nshareDistriShopGoodsDaily',
        // 社区分享店铺配送商品每日信息
        nshareDistriShopGoodsDailyTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '商品',
              key: 'goodsId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '标准价格',
              key: 'normPrice',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '销售价格',
              key: 'salePrice',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '单位',
              key: 'priceUnit',
              type: FormTypes.select,
              dictCode:"mp_ns_goods_unit",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '是否上架',
              key: 'onSale',
              type: FormTypes.select,
              dictCode:"mp_ns_goods_onsale",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '销售日期',
              key: 'saleDate',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/nshare.goods/nshareDistriShopGoods/add",
          edit: "/nshare.goods/nshareDistriShopGoods/edit",
          nshareDistriShopGoodsDaily: {
            list: '/nshare.goods/nshareDistriShopGoods/queryNshareDistriShopGoodsDailyByMainId'
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
        let fieldval = pick(this.model,'goodsName','goodsDesc','shopId')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.nshareDistriShopGoodsDaily.list, params, this.nshareDistriShopGoodsDailyTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          nshareDistriShopGoodsDailyList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'goodsName','goodsDesc','shopId'))
     },

    }
  }
</script>

<style scoped>
</style>