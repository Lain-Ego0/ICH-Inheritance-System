<template>
    <div>
        <div v-if="detail != null" class="card RightSideBar padding-xs">
            <div class="marign-top-xs line-bottom text-sm text-bold">作者</div>
            <el-divider></el-divider>

            <div v-if="detail.IsSecrecy">
                <el-empty description="作者信息未公开"></el-empty>
            </div>


            <div v-else>
                <div class="flex padding-xs">
                    <img :src="detail.ImageUrls ? detail.ImageUrls : require('@/assets/默认头像.png')" class="avator-img">
                    <div class="flex-sub margin-left-xs">
                        <div><span class="text-bold ">{{ detail.Name }}</span>
                        </div>
                    </div>
                </div>
                <div v-if="TopicInfoCard != null" class="flex justify-between align-center margin-top-sm">
                    <div class="flex flex-direction justify-center align-center">
                        <div class="text-bold text-mini"> <i class="el-icon-fenxiang"></i>
                            <span class="">分享</span>
                        </div>
                        <div class="margin-top-xs"> {{ TopicInfoCard.articleCount }}</div>
                    </div>
                    <div class="flex flex-direction justify-center align-center">
                        <div class="text-bold text-mini"><i class="el-icon-liulan"></i><span class="">浏览人次</span>
                        </div>
                        <div class="margin-top-xs">{{ TopicInfoCard.readCount }}</div>
                    </div>
                    <div class="flex flex-direction justify-center align-center">
                        <div class="text-bold text-mini"><i class="el-icon-shoucang2"></i> <span class="">收藏人数</span>
                        </div>
                        <div class="margin-top-xs">{{ TopicInfoCard.collectCount }}</div>
                    </div>
                </div>

                <div class="flex justify-between align-center margin-top-xs">

                    <el-button type="warning" size="mini" @click="ToTaHome()">前往主页</el-button>
                </div>
            </div>


        </div>

    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex'
export default {
    name: "RelativeAuthorCard",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType'
        ])
    },
    props: {
        UserId: {

        },

    },
    data() {
        return {
            detail: null,
            TopicInfoCard: null,

        };
    },
    beforeCreate(e) {

    },
    created() {
        this.GetUserInfo();
        this.MyTopicInfoCard();
    },
    methods: {
        async GetUserInfo() {
            let { Data } = await this.$Post(`/User/Get`, { Id: this.UserId });
            this.detail = Data;
        },
        async ToTaHome(Id) {
            this.$router.push({
                path: `/Front/TaHome`, query: {
                    UserId: this.UserId,

                }
            });
        },
        async MyTopicInfoCard() {
            let { Data } = await this.$Post(`/Topic/MyTopicInfoCard`, { Id: this.UserId });
            this.TopicInfoCard = Data;
        },


    },

};
</script>
<style scoped>
.avator-img {
    width: 64px;
    height: 64px;
    border-radius: 5px;
}
</style>
