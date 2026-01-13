<template>
    <div v-if="Detail != null" class="main-container">

        <el-page-header class="card " @back="goBack" content="话题详情">
        </el-page-header>

        <div class="QuestionHeader margin-top-xs">
            <div class="QuestionHeaderContent">
                <el-tag>{{ Detail.TopicTypeDto.Name }}</el-tag>
                <div class="QuestionHeader-title">
                    {{ Detail.Title }}
                </div>
                <div v-if="Detail.ImageUrls">
                    <el-carousel :interval="5000" height="480px">
                        <el-carousel-item v-for="(item, index) in Detail.ImageUrlList">
                            <img class="banner-img" :src="item.url"></img>
                        </el-carousel-item>

                    </el-carousel>
                </div>
                <div>
                    <div class="rich_content" v v-html="Detail.Content"></div>
                    <div class="flex align-center margin-top-xs margin-bottom-lg" v-if="Token">
                        <el-button icon="el-icon-star-off" :type="CollectId > 0 ? 'warning' : 'primary'" size="mini"
                            @click="CollectBtn()">{{ CollectId > 0 ? '取消收藏' : '收藏' }}</el-button>
                        <div class="share-section">
                            <div class="share-title">
                                <i class="el-icon-share"></i>
                                <span>分享到</span>
                            </div>
                            <div class="share-buttons">
                                <div class="share-btn qq" @click="shareToSocial('qq')">
                                    <img :src="require('@/assets/qq.png')" alt="QQ">
                                    <span>QQ</span>
                                </div>
                                <div class="share-btn weibo" @click="shareToSocial('weibo')">
                                    <img :src="require('@/assets/微博.png')" alt="微博">
                                    <span>微博</span>
                                </div>
                                <div class="share-btn link" @click="copyLink">
                                    <img :src="require('@/assets/复制链接.png')" alt="复制链接">
                                    <span>复制链接</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="text-gray">编辑于 {{ Detail.CreationTime }}</div>

                </div>
            </div>
        </div>
        <div class="recommend-section margin-top-lg">
            <div class="recommend-title">
                <i class="el-icon-star-on"></i>
                <span>你可能感兴趣的</span>
            </div>
            <PaginationBox ref="TopicPaginationBox" :where="{ Id: Detail.Id }" url="/Topic/ContentRecommendTop10"
                :limit="4">
                <template v-slot:content="{ data }">
                    <div class="recommend-list">
                        <div v-for="(item, index) in data" :key="index" class="recommend-topic-item"
                            @click="SwitchTopic(item.Id)">
                            <div class="recommend-topic-header">
                                <el-tag size="small" type="info">{{ item.TopicTypeDto.Name }}</el-tag>
                                <div class="recommend-topic-title">{{ item.Title }}</div>
                            </div>
                            <div class="recommend-topic-content">
                                <div class="recommend-topic-cover" v-if="item.Cover">
                                    <img :src="item.Cover" alt="话题封面">
                                </div>
                                <div class="recommend-topic-info">
                                    <div class="recommend-topic-author">
                                        <img class="author-avatar"
                                            :src="item.CreatorAppUserDto.ImageUrls || require('@/assets/默认头像.png')"
                                            alt="作者头像">
                                        <span class="author-name">{{ item.CreatorAppUserDto.Name }}</span>
                                    </div>
                                    <div class="recommend-topic-stats">
                                        <span class="recommend-topic-time"><i class="el-icon-time"></i> {{ item.CreationTime
                                        }}</span>
                                        <span class="recommend-topic-view" v-if="item.ViewCount"><i
                                                class="el-icon-view"></i> {{ item.ViewCount }}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </template>
            </PaginationBox>
        </div>
        <div class="Topstory-container">
            <div class="Topstory-mainColumn">

                <div class="Topstory-content">
                    <CommentTree :RelativeId="Detail.Id" Type="Topic"></CommentTree>
                </div>
            </div>
            <div class="side">
                <RelativeAuthorCard :UserId="Detail.CreatorId"></RelativeAuthorCard>

            </div>

        </div>

    </div>
</template>

<script>
import RelativeAuthorCard from "@/views/Front/RelativeAuthorCard.vue";
import { mapGetters } from 'vuex';

import PaginationBox from '@/components/Pagination/PaginationBox.vue';
import CommentTree from "@/views/Front/CommentTree.vue";
import RecommendAuthorListCard from "@/views/Front/RecommendAuthorListCard.vue";
export default {
    name: 'TopicDetail',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {
        RelativeAuthorCard,
        CommentTree,
        RecommendAuthorListCard,
        PaginationBox
    },
    data() {
        return {
            Detail: null,
            CollectId: null,
        };
    },
    watch: { // 监听，当路由发生变化的时候执行
        $route(to, from) {
            if (to.query.TopicId != from.query.TopicId) {
                this.GetDetailApi();
                this.GetTopicCollectApi();
            }
        },
    },
    mounted() {
        this.GetDetailApi();
        this.GetTopicCollectApi();
    },
    methods: {
        async SwitchTopic(topicId) {
            this.$router.push({
                path: '/Front/TopicDetail',
                query: { TopicId: topicId }
            });
        },
        async GetDetailApi() {
            let { Data } = await this.$Post(`/Topic/Get`, { Id: this.$route.query.TopicId });
            Data.ImageUrlList = this.$Comm.FullConvertUrlArray(Data.ImageUrls);

            this.Detail = Data;
            this.TopicRecordApi();


        },
        async TopicRecordApi() {
            if (this.Token) {
                let { Data } = await this.$Post(`/TopicRecord/CreateOrEdit`, {
                    TopicTypeId: this.Detail.TopicTypeId,
                    TopicId: this.$route.query.TopicId,
                    UserId: this.UserId,

                });
            }

        },
        async GetTopicCollectApi() {
            if (this.UserId > 0) {
                let {
                    Data: {
                        Items
                    }
                } = await this.$Post("/TopicCollect/List", {
                    UserId: this.UserId,
                    TopicId: this.$route.query.TopicId
                });
                this.CollectId = Items.length > 0 ? Items[0].Id : null;

            }

        },
        /**
         * 收藏或者取消按钮
         */
        async CollectBtn() {
            if (this.$Comm.CheckIsLogin()) {
                if (this.CollectId) {
                    await this.$Post("/TopicCollect/Delete", {
                        Id: this.CollectId
                    })
                    this.CollectId = null;
                    this.$message.success("取消收藏");

                } else {
                    let {
                        Data
                    } = await this.$Post("/TopicCollect/CreateOrEdit", {
                        UserId: this.UserId,
                        TopicId: this.$route.query.TopicId
                    })
                    this.$message.success("收藏成功");
                    this.CollectId = Data.Id;
                }
            }
        },
        // 添加分享到社交媒体的方法
        shareToSocial(platform) {
            if (!this.Detail) return;

            const url = encodeURIComponent(window.location.href);
            const title = encodeURIComponent(this.Detail.Title);
            const description = encodeURIComponent(this.Detail.Content.substring(0, 100) + '...');
            const pic = this.Detail.ImageUrlList && this.Detail.ImageUrlList.length > 0 ?
                encodeURIComponent(this.Detail.ImageUrlList[0].url) : '';

            let shareUrl = '';

            switch (platform) {
                case 'qq':
                    shareUrl = `http://connect.qq.com/widget/shareqq/index.html?url=${url}&title=${title}&summary=${description}&pics=${pic}`;
                    break;
                case 'weibo':
                    shareUrl = `http://service.weibo.com/share/share.php?url=${url}&title=${title}&pic=${pic}`;
                    break;
            }

            if (shareUrl) {
                window.open(shareUrl, '_blank');
            }
        },
        // 复制链接
        copyLink() {
            const path = window.location.href;
            const el = document.createElement('textarea');
            el.value = path;
            document.body.appendChild(el);
            el.select();
            document.execCommand('copy');
            document.body.removeChild(el);
            this.$message.success("已经复制到剪切板了,赶紧分享给你的好友吧！");
        },
        goToTopic(topicId) {
            if (topicId === this.$route.query.TopicId) return;
            this.$router.push({
                path: '/TopicDetail',
                query: { TopicId: topicId }
            });
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
    width: 100%;

}


.Topstory-mainColumn {
    -ms-flex-negative: 0;
    flex-shrink: 0;
    margin-bottom: 0;
    margin-right: 10px;
    flex: 1;

}

.RightSideBar {
    width: 296px;
    -webkit-align-self: normal;
    -ms-flex-item-align: normal;
    align-self: normal;
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
    color: #1772f6;
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

.QuestionHeader {
    background: #fff;
    min-width: 1032px;

    overflow: hidden;
    padding: 16px 0;
    position: relative;
}

.QuestionHeaderContent {
    margin: 10px auto;
    padding: 0 16px;
    position: relative;
    width: var(--content-width);
}

.QuestionHeader-title {
    color: #191b1f;
    line-height: 32px;
    font-size: 22px;
    font-synthesis: style;
    font-weight: 600;
    margin-bottom: 4px;
    margin-top: 12px;
}

.rich_content {
    color: #2a2a2a;
    font-size: 16px;
    line-height: 32px;
    word-break: break-all;
    word-wrap: break-word;
    position: relative;
}

.comment_content {
    margin-top: 10px;
    color: #2a2a2a;
    font-size: 16px;
    line-height: 32px;
    word-break: break-all;
    word-wrap: break-word;
    position: relative;
    overflow: hidden;
    width: 100%;


}

.children_comment_list {
    margin-left: 40px;
}

.avator-img {
    width: 64px;
    height: 64px;
    border-radius: 5px;
}

.banner-img {
    border-radius: 20px;
    width: 100%;
    height: 480px;
    overflow: hidden;
    object-fit: scale-down;
}

/* 推荐话题样式 */
.recommend-section {
    background-color: #fff;
    border-radius: 8px;
    padding: 16px;
    margin-bottom: 20px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.recommend-title {
    font-size: 18px;
    font-weight: 600;
    color: #333;
    margin-bottom: 16px;
    padding-bottom: 10px;
    border-bottom: 1px solid #f0f0f0;
    display: flex;
    align-items: center;
}

.recommend-title i {
    color: #ff9900;
    margin-right: 8px;
    font-size: 20px;
}

.recommend-list {
    width: 100%;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 16px;
}

.recommend-topic-item {
    padding: 12px;
    border: 1px solid #f0f0f0;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s;
    background-color: #fff;
    display: flex;
    flex-direction: column;
}

.recommend-topic-item:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transform: translateY(-2px);
}

.recommend-topic-header {
    margin-bottom: 12px;
}

.recommend-topic-title {
    font-size: 16px;
    font-weight: 600;
    margin-top: 8px;
    color: #333;
    line-height: 1.5;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}

.recommend-topic-content {
    display: flex;
    align-items: flex-start;
    flex: 1;
}

.recommend-topic-cover {
    width: 100px;
    height: 70px;
    margin-right: 12px;
    overflow: hidden;
    border-radius: 4px;
    flex-shrink: 0;
}

.recommend-topic-cover img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s;
}

.recommend-topic-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    min-height: 70px;
}

.recommend-topic-author {
    display: flex;
    align-items: center;
}

.author-avatar {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    margin-right: 8px;
    object-fit: cover;
}

.author-name {
    font-size: 14px;
    color: #666;
}

.recommend-topic-stats {
    display: flex;
    align-items: center;
    margin-top: 8px;
    font-size: 12px;
    color: #999;
}

.recommend-topic-time {
    margin-right: 12px;
}

.recommend-topic-time i,
.recommend-topic-view i {
    margin-right: 4px;
}

.margin-top-lg {
    margin-top: 20px;
}

/* 分享区域样式 */
.share-section {
    display: inline-flex;
    align-items: center;
    background: #f8f9fa;
    border-radius: 8px;
    padding: 8px 15px;
    margin-left: 10px;
}

.share-title {
    font-size: 14px;
    font-weight: 500;
    margin-right: 12px;
    color: #333;
    display: flex;
    align-items: center;
}

.share-title i {
    margin-right: 6px;
    color: #409EFF;
}

.share-buttons {
    display: flex;
    gap: 15px;
}

.share-btn {
    display: flex;
    flex-direction: column;
    align-items: center;
    cursor: pointer;
    padding: 4px 8px;
    border-radius: 6px;
    transition: all 0.3s;
}

.share-btn img {
    width: 24px;
    height: 24px;
    margin-bottom: 4px;
    object-fit: contain;
    border-radius: 4px;
    transition: transform 0.2s ease;
}

.share-btn span {
    font-size: 12px;
}

.share-btn:hover {
    background: rgba(0, 0, 0, 0.05);
}

.share-btn:hover img {
    transform: scale(1.1);
}

.share-btn.qq {
    color: #12B7F5;
}

.share-btn.weibo {
    color: #E6162D;
}

.share-btn.link {
    color: #409EFF;
}

/* 确保分享按钮的父容器是相对定位 */
.flex.align-center {
    position: relative;
}
</style>
