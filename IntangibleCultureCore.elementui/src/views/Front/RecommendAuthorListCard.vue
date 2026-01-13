<template>
    <div>
        <div class="card RightSideBar padding-xs">
            <div class="marign-top-xs line-bottom text-sm text-bold">推荐相关博主</div>
            <el-divider></el-divider>
            <el-empty v-if="AppUserList.length == 0" description="看官，数据正在维护中"></el-empty>
            <div v-for="(item, index) in AppUserList">

                <div class="flex padding-xs">
                    <img :src="item.userInfo.ImageUrls" class="avator-img">
                    <div class="flex-sub margin-left-xs">
                        <div><span class="text-bold ">{{ item.userInfo.Name }}</span>
                        </div>
                        <div><span class="text-gray text-lt content-cut ">{{ item.userInfo.Introduce }}</span></div>
                    </div>
                </div>
                <div class="flex justify-between align-center margin-top-sm">
                    <div class="flex flex-direction justify-center align-center">

                        <div class="text-bold text-mini"><i class="el-icon-zuopin"></i> <span class="">话题数</span></div>
                        <div class="margin-top-xs">{{ item.topicCount }}</div>
                    </div>
                    <div class="flex flex-direction justify-center align-center">
                        <div class="text-bold text-mini"><i class="el-icon-liulan"></i><span class="">浏览人次</span>
                        </div>
                        <div class="margin-top-xs">{{ item.readCount }}</div>
                    </div>
                    <div class="flex flex-direction justify-center align-center">
                        <div class="text-bold text-mini"><i class="el-icon-shoucang2"></i> <span class="">收藏人数</span>
                        </div>
                        <div class="margin-top-xs">{{ item.collectCount }}</div>
                    </div>
                </div>
                <div v-if="item.topics.length > 0" class="pointer padding-xs" style="border-bottom: 1px solid gray;">
                    <div v-for="(item, index) in item.topics" @click="TopicClick(item.Id)">
                        <div class="flex justify-between  margin-bottom-xs text-lt">
                            <div style="flex:1;font-size: 14px;" class="text-cut">{{ item.Title }}</div>
                            <div style="width: 50px;font-size: 14px;">{{ item.ViewCount }}</div>
                        </div>
                    </div>
                </div>
                <div class="flex justify-between align-center margin-top-xs">
                    <el-button type="warning" size="mini" @click="ToTaHome(item.userInfo.Id)">前往主页</el-button>
                </div>
                <el-divider></el-divider>
            </div>


        </div>

    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex'
export default {
    name: "RecommendAuthorListCard",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    props: {

    },
    data() {
        return {
            detail: null,
            AppUserList: null,
            lock: 0,//用来防止重复提交的变量
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,//编辑弹窗定义的数据对象
            editModalFormRules: {

                "Content": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

            },
        };
    },
    beforeCreate(e) {

    },
    created() {

        this.MostActiveTopicOfBlogger();
    },
    methods: {


        async MostActiveTopicOfBlogger() {
            let { Data: { datas } } = await this.$Post(`/Topic/MostActiveTopicOfBlogger`, { Id: this.UserId });
            this.AppUserList = datas;
        },
        TopicClick(Id) {
            this.$router.push({
                path: `/Front/TopicDetail`, query: {
                    TopicId: Id,

                }
            });
        },
        ToTaHome() {
            this.$router.push({
                path: `/Front/TaHome`, query: {
                    UserId: this.UserId,

                }
            });
        },



    },

};
</script>
<style lang="scss" scoped>
.RightSideBar {
    width: 396px;
    -webkit-align-self: normal;
    -ms-flex-item-align: normal;
    align-self: normal;
}

.avator-img {
    width: 64px;
    height: 64px;
    border-radius: 5px;
}

.content-cut {

    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;

    line-height: 1.2em;
}
</style>
