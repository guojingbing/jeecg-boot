<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="店铺" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['shopId', validatorRules.shopId]" :trigger-change="true" dictCode="nshare_distri_shop,shop_name,id" placeholder="请选择店铺"/>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'goodsName', validatorRules.goodsName]" placeholder="请输入商品名称"></a-input>
        </a-form-item>
        <a-form-item label="商品代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'goodsCode', validatorRules.goodsCode]" placeholder="请输入商品代码"></a-input>
        </a-form-item>
        <a-form-item label="商品分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'goodsCat', validatorRules.goodsCat]" placeholder="请输入商品分类"></a-input>
        </a-form-item>
        <a-form-item label="商品描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'goodsDesc', validatorRules.goodsDesc]" placeholder="请输入商品描述"></a-input>
        </a-form-item>
        <a-form-item label="标准价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'normPrice', validatorRules.normPrice]" placeholder="请输入标准价格" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="销售价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'salePrice', validatorRules.salePrice]" placeholder="请输入销售价格" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'priceUnit', validatorRules.priceUnit]" placeholder="请输入单位"></a-input>
        </a-form-item>
        <a-form-item label="是否上架" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['onSale', validatorRules.onSale]" :trigger-change="true" dictCode="" placeholder="请选择是否上架"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "NshareDistriShopGoodsModal",
    components: { 
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          shopId: {rules: [
          ]},
          goodsName: {rules: [
          ]},
          goodsCode: {rules: [
          ]},
          goodsCat: {rules: [
          ]},
          goodsDesc: {rules: [
          ]},
          normPrice: {rules: [
          ]},
          salePrice: {rules: [
          ]},
          priceUnit: {rules: [
          ]},
          onSale: {rules: [
          ]},
        },
        url: {
          add: "/goods/nshareDistriShopGoods/add",
          edit: "/goods/nshareDistriShopGoods/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'shopId','goodsName','goodsCode','goodsCat','goodsDesc','normPrice','salePrice','priceUnit','onSale'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'shopId','goodsName','goodsCode','goodsCat','goodsDesc','normPrice','salePrice','priceUnit','onSale'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>