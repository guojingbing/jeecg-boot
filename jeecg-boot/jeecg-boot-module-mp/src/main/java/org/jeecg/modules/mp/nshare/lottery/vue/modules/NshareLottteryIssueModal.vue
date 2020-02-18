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
            <a-form-item label="彩票编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'lotteryId', validatorRules.lotteryId]" placeholder="请输入彩票编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="期号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'issueNo', validatorRules.issueNo]" placeholder="请输入期号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="开奖号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'number', validatorRules.number]" placeholder="请输入开奖号码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="特别号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'speNumber', validatorRules.speNumber]" placeholder="请输入特别号码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="销售额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'saleAmount', validatorRules.saleAmount]" placeholder="请输入销售额" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="奖池金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'jackpot', validatorRules.jackpot]" placeholder="请输入奖池金额" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="开奖日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择开奖日期" v-decorator="[ 'openDate', validatorRules.openDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="兑奖截止日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择兑奖截止日期" v-decorator="[ 'deadLine', validatorRules.deadLine]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="社区分享彩票奖项" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="nshareLottteryIssuePrizeTable.loading"
            :columns="nshareLottteryIssuePrizeTable.columns"
            :dataSource="nshareLottteryIssuePrizeTable.dataSource"
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

  export default {
    name: 'NshareLottteryIssueModal',
    mixins: [JEditableTableMixin],
    components: {
      JDate,
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
          lotteryId:{},
          issueNo:{},
          number:{},
          speNumber:{},
          saleAmount:{},
          jackpot:{},
          openDate:{},
          deadLine:{},
        },
        refKeys: ['nshareLottteryIssuePrize', ],
        tableKeys:['nshareLottteryIssuePrize', ],
        activeKey: 'nshareLottteryIssuePrize',
        // 社区分享彩票奖项
        nshareLottteryIssuePrizeTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '彩票种类',
              key: 'lotteryId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '开奖期数',
              key: 'issueId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '奖项名称',
              key: 'prizeName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '中奖规则',
              key: 'prizeRule',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '单注奖金',
              key: 'perBonus',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '中奖人数',
              key: 'num',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/nshare.lottery/nshareLottteryIssue/add",
          edit: "/nshare.lottery/nshareLottteryIssue/edit",
          nshareLottteryIssuePrize: {
            list: '/nshare.lottery/nshareLottteryIssue/queryNshareLottteryIssuePrizeByMainId'
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
        let fieldval = pick(this.model,'lotteryId','issueNo','number','speNumber','saleAmount','jackpot','openDate','deadLine')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.nshareLottteryIssuePrize.list, params, this.nshareLottteryIssuePrizeTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          nshareLottteryIssuePrizeList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'lotteryId','issueNo','number','speNumber','saleAmount','jackpot','openDate','deadLine'))
     },

    }
  }
</script>

<style scoped>
</style>