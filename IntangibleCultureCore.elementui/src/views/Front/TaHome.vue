<template>
    <div v-if="Detail != null" class="main-container">
        <div class="Topstory-container">
            <div class="Topstory-mainColumn  ">
                <div class="flex flex-direction align-center justify-center card">
                    <img style="width:160px;height: 160px;border-radius: 50%;" :src="Detail.ImageUrls">
                    <div class="margin-top-xs text-bold">{{ Detail.Name }}</div>
                    <div class="margin-top-xs">{{ Detail.Introduce }}</div>
                </div>
                <div class="card margin-top-xs">
                    <div class="header-tit ">博主发的文章</div>
                </div>

                <div class="margin-top-xs   pointer card">
                    <PaginationBox ref="PaginationBox" :where="where" url="/Topic/List">
                        <template v-slot:content="{ data }">
                            <TopicListCard :DataList="data"></TopicListCard>
                        </template>
                    </PaginationBox>

                </div>

            </div>

            <RecommendAuthorListCard></RecommendAuthorListCard>


        </div>

    </div>
</template>

<script>

import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import RecommendAuthorListCard from "@/views/Front/RecommendAuthorListCard.vue";
import TopicListCard from "@/views/Front/TopicListCard.vue";
import { mapGetters } from 'vuex';
export default {
    components: { RecommendAuthorListCard, TopicListCard, PaginationBox },
    name: 'TaHome',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },

    data() {

        return {

            where: {
                AuditStatus: 2,
            },
            Detail: null,


        };
    },
    beforeMount() {
        this.where.CreatorId = this.$route.query.UserId;
    },
    mounted() {
        this.GetUserInfoApi();

    },
    methods: {
        async GetUserInfoApi() {
            let { Data } = await this.$Post(`/User/Get`, { Id: this.$route.query.UserId });
            this.Detail = Data;
        },




    }


}
</script>

<style lang="scss" scoped>
.Topstory-container {
    -webkit-box-align: start;
    -ms-flex-align: start;
    align-items: flex-start;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    margin: 10px auto;

    position: relative;

}

.Topstory-mainColumn {
    -ms-flex-negative: 0;
    flex-shrink: 0;
    margin-bottom: 0;
    margin-right: 10px;
    flex: 1;
}



.Topstory-tabs {
    border-bottom: 1px solid #f8f8fa;
}

.TopstoryTabs {
    height: 58px;
    display: flex;
}

.TopstoryTabs-link {
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    font-size: 16px;
    margin: 0 22px;
}

.TopstoryTabs-link.is-active {
    font-weight: 500;
    color: #09b6c5;
}

.TopstoryItem {
    border-radius: 0;
    outline: none;
    overflow: visible;
    overflow: initial;
    padding: 16px 20px;
    position: relative;
    border-bottom: 1px solid #f8f8fa;
    padding-bottom: 30px;
}

.ContentItem-title {
    margin-bottom: -4px;
    margin-top: -4px;
    word-break: break-word;
    color: #191b1f;
    line-height: 1.6;
    font-size: 18px;
    font-synthesis: style;
    font-weight: 600;
}

.AuthorInfo {
    -webkit-box-align: center;
    -ms-flex-align: center;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    align-items: center;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    justify-content: space-between;
    max-width: 654px;
    position: relative;
}

.AuthorInfo-avatar {
    box-sizing: border-box;
    margin: 0px;
    min-width: 0px;
    max-width: 100%;
    background-color: rgb(255, 255, 255);
    width: 24px;
    height: 24px;
    border-radius: 2px;
}

.AuthorInfo-name {
    font-synthesis: style;
    font-weight: 600;
}

.AuthorInfo-badgeText {
    color: #535861;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.ContentItem-time {
    color: #8491a5;
    font-size: 14px;
    margin-top: 10px;
}

.content-cut {

    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 4;

    line-height: 1.2em;
}



.CreatorEntrance {
    height: 52px;
    width: 100%;
    display: flex;
    align-items: center;
    padding: 10px;
}
</style>
