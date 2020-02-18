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

        <a-form-item label="拼音" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'pinyin', validatorRules.pinyin]" placeholder="请输入拼音"></a-input>
        </a-form-item>
        <a-form-item label="成语名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'title', validatorRules.title]" placeholder="请输入成语名称"></a-input>
        </a-form-item>
        <a-form-item label="释义" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'definition', validatorRules.definition]" placeholder="请输入释义"></a-input>
        </a-form-item>
        <a-form-item label="出处" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'source', validatorRules.source]" placeholder="请输入出处"></a-input>
        </a-form-item>
        <a-form-item label="例句" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'example', validatorRules.example]" placeholder="请输入例句"></a-input>
        </a-form-item>
        <a-form-item label="近义词" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'synonym', validatorRules.synonym]" placeholder="请输入近义词"></a-input>
        </a-form-item>
        <a-form-item label="反义词" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'antonym', validatorRules.antonym]" placeholder="请输入反义词"></a-input>
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
  
  export default {
    name: "TlKbIdiomModal",
    components: { 
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
          pinyin: {rules: [
          ]},
          title: {rules: [
          ]},
          definition: {rules: [
          ]},
          source: {rules: [
          ]},
          example: {rules: [
          ]},
          synonym: {rules: [
          ]},
          antonym: {rules: [
          ]},
        },
        url: {
          add: "/idiom/tlKbIdiom/add",
          edit: "/idiom/tlKbIdiom/edit",
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
          this.form.setFieldsValue(pick(this.model,'pinyin','title','definition','source','example','synonym','antonym'))
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
        this.form.setFieldsValue(pick(row,'pinyin','title','definition','source','example','synonym','antonym'))
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