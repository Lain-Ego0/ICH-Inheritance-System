<template>
    <div class="main-container">

        <PaginationBox ref="PaginationTableId" :where="where" url="/TopicCollect/List">
            <template v-slot:content="{ data }">
                <el-row :gutter="20" style="width: 100%;">
                    <el-col :span="8" v-for="item in data" :key="item.Id">
                        <el-card class="topic-card" :body-style="{ padding: '0px' }">
                            <div class="image-container">
                                <img :src="item.TopicDto.Cover" class="cover-image">
                            </div>
                            <div class="card-content">
                                <h3 class="topic-title">{{ item.TopicDto.Title }}</h3>
                                <div class="topic-info">
                                    <span class="view-count">
                                        <i class="el-icon-view"></i> {{ item.TopicDto.ViewCount }}
                                    </span>
                                    <span class="creation-time">
                                        {{ item.CreationTime.split(' ')[0] }}
                                    </span>
                                </div>
                                <div class="topic-content" v-html="item.TopicDto.Content"></div>
                                <div class="card-actions">
                                    <el-button type="text" @click="Look(item)">查看详情</el-button>
                                    <el-button type="text" @click="ShowDeleteModal(item.Id)" class="delete-btn">
                                        取消收藏
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
    name: "TopicCollectList",
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
            where: {},
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },
                {
                    key: "TopicId",
                    hidden: true,
                },
                {
                    key: "TopicDto.Title",
                    title: "话题标题",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "TopicDto.Cover",
                    title: "封面",
                    type: store.getters.ColumnType.IMAGES,
                },
                {
                    key: "TopicDto.ImageUrls",
                    title: "主图",
                    type: store.getters.ColumnType.IMAGES,
                },
                {
                    key: "TopicDto.Content",
                    title: "套餐介绍",
                    width: "140px",
                    type: store.getters.ColumnType.RICHTEXT,
                },
                {
                    key: "CollectUserId",
                    hidden: true,
                },
                {
                    key: "UserDto.Name",
                    title: "用户名称",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "TopicId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CollectUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,

        };
    },
    created() {
        this.where.CollectUserId = this.UserId;
    },
    methods: {
        //修改属性
        async Look(item) {
            this.$router.replace({
                path: '/Front/TopicDetail',
                query: {
                    TopicId: item.TopicId,

                }
            })
        },


        /**
         * 单个删除的时候会触发
         */
        async ShowDeleteModal(Id) {
            await this.$PostDelete(`/TopicCollect/Delete`, { Id: Id });
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
            await this.$PostDelete(`/TopicCollect/BatchDelete`, { Ids: ids });

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },




    },
};
</script>

<style scoped>
.topic-card {
    margin-bottom: 20px;
    transition: all 0.3s;
}

.topic-card:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transform: translateY(-2px);
}

.image-container {
    height: 200px;
    overflow: hidden;
}

.cover-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.card-content {
    padding: 14px;
}

.topic-title {
    margin: 0;
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 10px;
}

.topic-info {
    display: flex;
    justify-content: space-between;
    color: #999;
    font-size: 12px;
    margin-bottom: 10px;
}

.topic-content {
    color: #666;
    font-size: 14px;
    margin-bottom: 10px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
}

.card-actions {
    border-top: 1px solid #eee;
    padding-top: 10px;
    display: flex;
    justify-content: space-between;
}

.delete-btn {
    color: #F56C6C;
}
</style>