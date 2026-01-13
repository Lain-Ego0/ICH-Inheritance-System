<template>
    <div class="app-container">
        <el-card class="box-card">            
            <div slot="header" class="clearfix">
          		   <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="名称" prop="Name">
                        <el-input v-model.trim="searchForm.Name"  placeholder="请输入名称"  :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="编号" prop="No">
                        <el-input v-model.trim="searchForm.No"  placeholder="请输入编号"  :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="申报地区" prop="PlaceDeclaration">
                        <el-input v-model.trim="searchForm.PlaceDeclaration"  placeholder="请输入申报地区"  :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="保护单位" prop="ProtectionUnit">
                        <el-input v-model.trim="searchForm.ProtectionUnit"  placeholder="请输入保护单位"  :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="省市区" prop="ProviceCityArea">
                        <el-input v-model.trim="searchForm.ProviceCityArea"  placeholder="请输入省市区"  :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="批次" prop="BatchNo">
                        <el-input v-model.trim="searchForm.BatchNo"  placeholder="请输入批次"  :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="类别">
                        <SigleSelect url="/CulturalType/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.CulturalTypeId">
                        </SigleSelect>
                    </el-form-item>                
                    <el-form-item label="传人">
                        <SigleSelect url="/Inheritor/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.InheritorId">
                        </SigleSelect>
                    </el-form-item>                
                    <el-form-item label="登记日期">
                        <el-date-picker v-model="searchForm.RecordDateRange" type="daterange" start-placeholder="开始登记日期"
                            end-placeholder="结束登记日期" :default-time="['00:00:00']" value-format="yyyy-MM-dd HH:mm:ss ">
                        </el-date-picker>                                       
                    </el-form-item>                
                    <el-form-item label="详细内容" prop="Content">
                        <el-input v-model.trim="searchForm.Content"  placeholder="请输入详细内容"  :clearable="true"></el-input>
                    </el-form-item>
                </el-form>
            </div>

        </el-card>


        <el-dialog  :title="formData.Id ? '修改非遗文化' : '添加非遗文化'"   :visible.sync="editorShow" width="50%" :lock-scroll="true" height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                 <el-row :gutter="10" class="EditFromBody"   style="max-height: 600px;overflow-y: scroll"  >               
                    <el-col :span="24">
                        <el-form-item label="名称" prop="Name">
                            <el-input type="text" v-model.trim="formData.Name"  placeholder="请输入名称"     :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="编号" prop="No">
                            <el-input type="text" v-model.trim="formData.No"  placeholder="请输入编号"     :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="类别" prop="CulturalTypeId">
                          <SigleSelect url="/CulturalType/List" columnName="Name" columnValue="Id"  v-model="formData.CulturalTypeId" >
                          </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="传人" prop="InheritorId">
                          <SigleSelect url="/Inheritor/List" columnName="Name" columnValue="Id"  v-model="formData.InheritorId" >
                          </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="图片" prop="ImageUrls">
                           <UploadImages :limit="1" v-model="formData.ImageUrls"></UploadImages>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="登记日期" prop="RecordDate">
                            <el-date-picker v-model="formData.RecordDate" align="right" type="date" placeholder="选择时间" value-format="yyyy-MM-dd HH:mm:ss">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="申报地区" prop="PlaceDeclaration">
                            <el-input type="text" v-model.trim="formData.PlaceDeclaration"  placeholder="请输入申报地区"     :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="保护单位" prop="ProtectionUnit">
                            <el-input type="text" v-model.trim="formData.ProtectionUnit"  placeholder="请输入保护单位"     :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="省市区" prop="ProviceCityArea">
                            <AddressSelect v-model="formData.ProviceCityArea"></AddressSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="批次" prop="BatchNo">
                            <el-input type="text" v-model.trim="formData.BatchNo"  placeholder="请输入批次"     :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="详细内容" prop="Content">
                             <AIRichText v-model="formData.Content" ></AIRichText>
                        </el-form-item>
                    </el-col>
                </el-row>


                <el-row type="flex" justify="end" align="bottom">
                    <el-form-item>
                        <el-button type="primary" plain @click="CreateOrEditForm()">确 定</el-button>
                        <el-button @click="editorShow = false">取 消</el-button>
                    </el-form-item>
                </el-row>
            </el-form>

        </el-dialog>


        <PaginationTable ref="PaginationTableId" url="/Cultural/List" :column="dataColum" :where="where">
            <template v-slot:header>
                <el-button type="primary" size="mini"  plain icon="el-icon-edit" @click="ShowEditModal()">新 增</el-button>
                <el-button type="danger"  size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删 除</el-button>
 				 </template>
            <template v-slot:Operate="scope">
                <el-button class="margin-top-xs" type="primary" size="mini"  @click="ShowEditModal(scope.row.Id)">修 改</el-button>
                <el-button class="margin-top-xs" type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)">删 除</el-button>
            </template>
        </PaginationTable>
    </div>
</template>
    
<script>
import store from '@/store';
import { mapGetters } from 'vuex'
import AddressSelect from '@/components/Select/AddressSelect.vue';
import moment from 'moment'
export default {
    name: "CulturalList",
 	 computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {
        AddressSelect
    },
    data() {
		   
        return {
            where:{}, 
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },
               {
                    key: "Name",
                    title: "名称",
							  width: "160px",
							                   	  
                    type: store.getters.ColumnType.SHORTTEXT, 
                },
               {
                    key: "No",
                    title: "编号",
							  width: "160px",
							                   	  
                    type: store.getters.ColumnType.SHORTTEXT, 
                },
               {
                    key: "ImageUrls",
                    title: "图片",
                    type:store.getters.ColumnType.IMAGES,
                },
               {
                    key: "RecordDate",
                    title: "登记日期",
               		  width: "140px",
                    type: store.getters.ColumnType.DATE,
                },
               {
                    key: "PlaceDeclaration",
                    title: "申报地区",
							  width: "160px",
							                   	  
                    type: store.getters.ColumnType.SHORTTEXT, 
                },
               {
                    key: "ProtectionUnit",
                    title: "保护单位",
							  width: "160px",
							                   	  
                    type: store.getters.ColumnType.SHORTTEXT, 
                },
               {
                    key: "CulturalTypeId",
                    hidden: true, 
                },
               {
                    key: "CulturalTypeDto.Name",
                    title: "名称",
							  width: "160px",
							                   	  
                    type: store.getters.ColumnType.SHORTTEXT, 
                },
               {
                    key: "ProviceCityArea",
                    title: "省市区",
							  width: "160px",
							                   	  
                    type: store.getters.ColumnType.SHORTTEXT, 
                },
               {
                    key: "BatchNo",
                    title: "批次",
							  width: "160px",
							                   	  
                    type: store.getters.ColumnType.SHORTTEXT, 
                },
               {
                    key: "InheritorId",
                    hidden: true, 
                },
               {
                    key: "InheritorDto.Name",
                    title: "姓名",
							  width: "160px",
							                   	  
                    type: store.getters.ColumnType.SHORTTEXT, 
                },
               {
                    key: "Content",
                    title: "详细内容",
                    width: "140px",
                    type: store.getters.ColumnType.RICHTEXT,
                },
                {
                    title: "操作",
                  	  width:"300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
              "Name":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "No":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "CulturalTypeId":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "InheritorId":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "ImageUrls":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "RecordDate":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "PlaceDeclaration":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "ProtectionUnit":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "ProviceCityArea":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "BatchNo":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "Content":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,

        };
    },
    created() {

    },
    methods: {
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/Cultural/Get`, `/Cultural/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/Cultural/Get`, { Id: Id });
            this.formData = Data;
            
            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {
         
            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/Cultural/CreateOrEdit`, this.formData);
                  
                    if (Success) {
                        this.editorShow = false;
                        this.$refs.PaginationTableId.Reload(this.searchForm);
                    }
                }
            })
        },
        /**
         * 单个删除的时候会触发
         */
        async ShowDeleteModal(Id) {
            await this.$PostDelete(`/Cultural/Delete`, { Id: Id });
       		   this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 批量删除的时候会触发
         */
        async ShowBatchDeleteModal() {
            var ids = this.$refs.PaginationTableId.GetSelectionRow().map(x => x.Id);
            if (ids.length == 0) {
                this.$message.error("你选择需要删除的记录");
                return;
            }
            await this.$PostDelete(`/Cultural/BatchDelete`, { Ids: ids });
       		
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击表格搜索按钮会触发
         */
        async SearchClick() {
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击清空表单会触发
         */
        async ResetClick() {
            this.searchForm = {};
            this.$refs.PaginationTableId.Reload(this.searchForm);
        }
    },
};
</script>

<style scoped>
.app-container {
  background-color: #e0e0e0;
  min-height: 100vh;
  padding: 20px;
}

.box-card {
  background: #fff;
  border: 1px solid #ddd;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  border-radius: 8px;
  margin-bottom: 20px;
}

.tb-body {
  padding: 20px;
}

.el-form {
  margin-bottom: 20px;
}

.el-form-item {
  margin-bottom: 15px;
}

.el-dialog {
  border-radius: 8px;
}

.el-dialog__body {
  padding: 20px;
}

.el-button {
  margin-right: 10px;
}

@media (max-width: 768px) {
  .app-container {
    padding: 10px;
  }
  .tb-body {
    padding: 10px;
  }
  .el-form-item {
    width: 100%;
  }
}

/* 统一按钮颜色为侧边栏风格 */
.el-button--primary {
    background-color: #333;
    border-color: #333;
    color: #fff;
}

.el-button--primary:hover {
    background-color: #555;
    border-color: #555;
}

.el-button--warning {
    background-color: #333;
    border-color: #333;
    color: #fff;
}

.el-button--warning:hover {
    background-color: #555;
    border-color: #555;
}

.el-button--danger {
    background-color: #333;
    border-color: #333;
    color: #fff;
}

.el-button--danger:hover {
    background-color: #555;
    border-color: #555;
}
</style>
    