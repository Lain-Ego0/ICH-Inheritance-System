<template>
    <div class="main-container">

        <el-card class="box-card margin-top-xs">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-plus" @click="ShowEditModal()">添加话题</el-button>
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="标题" prop="Title">
                        <el-input v-model.trim="searchForm.Title" placeholder="请输入标题搜索"></el-input>
                    </el-form-item>

                </el-form>
            </div>

        </el-card>


        <el-dialog :title="formData.Id ? '修改话题' : '添加话题'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10">
                    <el-col :span="24">
                        <el-form-item label="标题" prop="Title">
                            <el-input v-model.trim="formData.Title"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="话题类型" prop="TopicTypeId">
                            <SigleSelect url="/TopicType/List" columnName="Name" columnValue="Id"
                                v-model="formData.TopicTypeId">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="封面" prop="Cover">
                            <UploadImages :limit="1" v-model="formData.Cover"></UploadImages>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="主图" prop="ImageUrls">
                            <UploadImages :limit="4" v-model="formData.ImageUrls"></UploadImages>
                        </el-form-item>
                    </el-col>


                    <el-col :span="24">
                        <el-form-item label="内容" prop="Content">
                            <AIRichText v-model="formData.Content"></AIRichText>
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

        <PaginationBox ref="PaginationTableId" url="/Topic/List" :where="{ CreatorId: UserId }">
            <template v-slot:content="{ data }">
                <el-row :gutter="20" style="width: 100%;">
                    <el-col :span="12" v-for="item in data" :key="item.Id">
                        <el-card class="topic-card" shadow="hover">
                            <div class="topic-header">
                                <el-image v-if="item.Cover" :src="item.Cover" class="topic-cover" fit="cover">
                                    <div slot="error" class="image-slot">
                                        <i class="el-icon-picture-outline"></i>
                                    </div>
                                </el-image>
                                <div class="topic-info">
                                    <h3 class="topic-title">{{ item.Title }}</h3>
                                    <div class="topic-meta">
                                        <span><i class="el-icon-view"></i> {{ item.ViewCount }}</span>
                                        <span><i class="el-icon-time"></i> {{ item.CreationTime }}</span>
                                        <el-tag size="small" :type="item.AuditStatus === 2 ? 'success' : 'warning'">
                                            {{ item.AuditStatusFormat }}
                                        </el-tag>
                                    </div>
                                </div>
                            </div>

                            <div v-if="item.ImageUrls" class="topic-images">
                                <el-image v-for="(url, index) in item.ImageUrls.split(',')" :key="index" :src="url"
                                    class="topic-image" fit="cover">
                                    <div slot="error" class="image-slot">
                                        <i class="el-icon-picture-outline"></i>
                                    </div>
                                </el-image>
                            </div>

                            <div class="topic-content" v-html="item.Content"></div>
                            <div class="topic-footer">
                                <el-tag size="small">{{ item.TopicTypeDto.Name }}</el-tag>
                                <div class="topic-actions">
                                    <el-button type="primary" size="small" icon="el-icon-edit"
                                        @click="ShowEditModal(item.Id)">
                                        编辑
                                    </el-button>
                                    <el-button type="danger" size="small" icon="el-icon-delete"
                                        @click="ShowDeleteModal(item.Id)">
                                        删除
                                    </el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </template>
        </PaginationBox>

    </div>
</template>

<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import store from '@/store';
import { mapGetters } from 'vuex';
export default {
    name: "TopicMy",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {
        PaginationBox
    },
    data() {

        return {
            lock: 0,//用来防止重复提交的变量
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },
                {
                    key: "AuditStatus",
                    hidden: true,
                },
                {
                    key: "Title",
                    title: "标题",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "TopicTypeDto.Name",
                    title: "话题类型",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Cover",
                    title: "封面",
                    type: store.getters.ColumnType.IMAGES,
                },
                {
                    key: "ImageUrls",
                    title: "主图",
                    type: store.getters.ColumnType.IMAGES,
                },
                {
                    key: "ViewCount",
                    title: "浏览数",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Content",
                    title: "内容",
                    type: store.getters.ColumnType.RICHTEXT,

                },
                {
                    key: "AuditStatusFormat",
                    title: "审核状态",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "TopicTypeId",
                    hidden: true,
                },
                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "Title": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "Cover": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "ImageUrls": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "ViewCount": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "Content": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "TopicTypeId": [{ required: true, message: '该项为必填项', trigger: 'blur' },
                ],

            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,//编辑弹窗定义的数据对象

        };
    },
    created() {

    },
    methods: {
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/Topic/Get`, `/Topic/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/Topic/Get`, { Id: Id });

            Data.AuditStatus = 1;
            Data.IsRecommand = false;
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/Topic/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/Topic/Delete`, { Id: Id });
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
            await this.$PostDelete(`/Topic/BatchDelete`, { Ids: ids });

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
        },



    },
};
</script>

<style scoped>
.topic-card {
    margin-bottom: 20px;
    transition: all 0.3s;
    height: 500px;
    overflow: hidden;
    display: flex;
    flex-direction: column;
}

.topic-card .el-card__body {
    flex: 1;
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

.topic-header {
    display: flex;
    margin-bottom: 15px;
}

.topic-cover {
    width: 120px;
    height: 120px;
    border-radius: 8px;
    margin-right: 15px;
}

.topic-info {
    flex: 1;
}

.topic-title {
    margin: 0 0 10px 0;
    font-size: 18px;
    color: #303133;
}

.topic-meta {
    display: flex;
    align-items: center;
    gap: 15px;
    color: #909399;
    font-size: 13px;
}

.topic-meta span {
    display: flex;
    align-items: center;
    gap: 4px;
}

.topic-content {
    color: #606266;
    margin-bottom: 15px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
}

.topic-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 15px;
    border-top: 1px solid #EBEEF5;
    margin-top: auto;
}

.topic-actions {
    display: flex;
    gap: 10px;
}

.image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: #f5f7fa;
    color: #909399;
    font-size: 30px;
}

.topic-images {
    display: flex;
    gap: 10px;
    margin-bottom: 15px;
    flex-wrap: wrap;
}

.topic-image {
    width: calc(25% - 8px);
    /* 4张图片每行，减去间距 */
    height: 120px;
    border-radius: 4px;
    object-fit: cover;
}

/* 当只有一张图片时的样式 */
.topic-images:has(> :only-child) .topic-image {
    width: 100%;
    height: 200px;
}

/* 当有两张图片时的样式 */
.topic-images:has(> :nth-child(2):last-child) .topic-image {
    width: calc(50% - 5px);
}

/* 当有三张图片时的样式 */
.topic-images:has(> :nth-child(3):last-child) .topic-image {
    width: calc(33.33% - 7px);
}
</style>
