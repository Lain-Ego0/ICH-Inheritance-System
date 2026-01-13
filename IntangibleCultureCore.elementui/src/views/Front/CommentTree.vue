<template>
    <div class="comment-container">
        <!-- 评论输入区域 -->
        <div class="comment-input-area Card">
            <h2 class="comment-title">留下精彩评论</h2>

            <template v-if="Token">
                <div class="comment-form">
                    <el-input type="textarea" :rows="5" placeholder="写下你的想法..." v-model="CommentContent"
                        class="comment-textarea" />
                    <el-button type="primary" class="submit-btn" :disabled="CommentContent == ''"
                        @click="SendContent(UserId, null, null)">
                        发表评论
                    </el-button>
                </div>
            </template>
            <template v-else>
                <el-empty description="登录后参与评论"></el-empty>
            </template>
        </div>

        <!-- 评论列表区域 -->
        <div class="comment-list-area Card">
            <h3 class="comment-subtitle">
                精彩评论
                <span class="comment-count" v-if="CommentList.length">{{ CommentList.length }}条评论</span>
            </h3>

            <el-empty description="暂无评论" v-if="CommentList.length == 0"></el-empty>

            <!-- 评论列表 -->
            <div class="comment-list" v-for="item in CommentList" :key="item.Id">
                <div class="comment-item">
                    <div class="comment-user">
                        <el-image :src="item.SendUserDto.ImageUrls || require('@/assets/默认头像.png')" class="user-avatar">
                            <div slot="error" class="image-slot">
                                <i class="el-icon-user"></i>
                            </div>
                        </el-image>
                        <div class="user-info">
                            <span class="user-name">{{ item.SendUserDto.Name }}</span>
                            <span class="comment-time">{{ item.CreationTime }}</span>
                        </div>
                    </div>

                    <div class="comment-content" v-html="item.Content"></div>

                    <div class="comment-actions">
                        <div class="action-left">
                            <span class="action-btn" :class="{ 'is-active': item.IsLove }"
                                @click="item.IsLove ? CancelLove(item) : Love(item)">
                                <i class="el-icon-star-on"></i>
                                {{ item.IsLove ? '已点赞' : '点赞' }}
                            </span>
                            <span class="action-btn" @click="ReplyComment(item)">
                                <i class="el-icon-chat-line-round"></i>
                                回复
                            </span>
                        </div>
                        <div class="action-right">
                            <span v-if="item.CreatorId == UserId" class="action-btn delete-btn"
                                @click="DeleteComment(item.Id)">
                                删除
                            </span>
                        </div>
                    </div>
                </div>

                <!-- 子评论列表 -->
                <div class="reply-list" v-if="item.Children && item.Children.length">
                    <div class="reply-item" v-for="reply in item.Children" :key="reply.Id">
                        <div class="comment-user">
                            <el-image :src="reply.SendUserDto.ImageUrls || require('@/assets/默认头像.png')"
                                class="user-avatar">
                                <div slot="error" class="image-slot">
                                    <i class="el-icon-user"></i>
                                </div>
                            </el-image>
                            <div class="user-info">
                                <div class="reply-user-info">
                                    <span class="user-name">{{ reply.SendUserDto.Name }}</span>
                                    <span class="reply-to" v-if="reply.TargetUserDto">
                                        回复
                                        <span class="target-name">{{ reply.TargetUserDto.Name }}</span>
                                    </span>
                                </div>
                                <span class="comment-time">{{ reply.CreationTime }}</span>
                            </div>
                        </div>

                        <div class="comment-content" v-html="reply.Content"></div>

                        <div class="comment-actions">
                            <div class="action-left">
                                <span class="action-btn" :class="{ 'is-active': reply.IsLove }"
                                    @click="reply.IsLove ? CancelLove(reply) : Love(reply)">
                                    <i class="el-icon-star-on"></i>
                                    {{ reply.IsLove ? '已点赞' : '点赞' }}
                                </span>
                                <span class="action-btn" @click="ReplyComment(reply)">
                                    <i class="el-icon-chat-line-round"></i>
                                    回复
                                </span>
                            </div>
                            <div class="action-right">
                                <span v-if="reply.CreatorId == UserId" class="action-btn delete-btn"
                                    @click="DeleteComment(reply.Id)">
                                    删除
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 回复弹窗 -->
        <el-dialog title="回复评论" :visible.sync="replyCommentShow" width="500px" class="reply-dialog">
            <el-form v-if="replyCommentShow" ref="editModalForm" :model="formData">
                <el-input type="textarea" :rows="4" placeholder="写下你的回复..." v-model="formData.Content"
                    class="reply-textarea" />
                <div class="dialog-footer">
                    <el-button @click="replyCommentShow = false">取 消</el-button>
                    <el-button type="primary" @click="ReplyCommentSubmit">确 定</el-button>
                </div>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import { CheckIsLogin } from '@/utils/comm';
import { mapGetters } from 'vuex';
export default {
    name: "CommentTree",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    props: {
        RelativeId: {

        },
        Type: {

        },
    },
    watch: {
        RelativeId: function (n, o) {
            if (n != o) {
                this.GetCommentApi();
            }
        }
    },
    data() {
        return {
            CommentContent: "",
            formData: {},
            CommentList: [],
            replyCommentShow: false,
        };
    },
    beforeCreate(e) {

    },
    created() {
        this.GetCommentApi();
    },
    methods: {
        async GetCommentApi() {
            let { Data: { Items, TotalCount } } = await this.$Post(`/Comment/RootList`, { RelativeId: this.$props.RelativeId, Type: this.$props.Type });
            this.TotalCount = TotalCount;
            this.CommentList = Items;
            this.$forceUpdate();
        },
        //发表内容
        async SendContent() {
            if (CheckIsLogin()) {
                let { Data } = await this.$Post(`/Comment/Get`, {});
                Data.Content = this.CommentContent;
                Data.RelativeId = this.$props.RelativeId;
                Data.NickName = this.UserInfo.Name;
                Data.HeadImage = this.UserInfo.ImageUrls;
                Data.SendUserId = this.UserId;
                Data.Type = this.$props.Type;
                Data.RootCommentId = 0;
                var { Success } = await this.$Post(`/Comment/CreateOrEdit`, Data);
                this.CommentContent = "";
                if (Success) {
                    this.$message.success("发表成功")
                    this.GetCommentApi();
                }
            }
        },
        //回复评论
        async ReplyComment(Comment) {
            if (CheckIsLogin()) {
                let Data = {};
                Data.RelativeId = this.$props.RelativeId;
                Data.NickName = this.UserInfo.Name;
                Data.HeadImage = this.UserInfo.ImageUrls;
                Data.SendUserId = this.UserId;
                Data.Type = this.$props.Type;
                Data.TargetUserId = Comment.SendUserId;
                Data.RootCommentId = Comment.RootCommentId != 0 ? Comment.RootCommentId : Comment.Id;
                this.formData = Data;
                this.replyCommentShow = true;
            }

        },
        //提交评论
        async ReplyCommentSubmit() {
            if (CheckIsLogin()) {

                var { Success } = await this.$Post(`/Comment/CreateOrEdit`, this.formData);
                this.formData = {};
                this.replyCommentShow = false;
                if (Success) {
                    this.$message.success("发表成功")
                    this.GetCommentApi();
                }

            }
        },
        //删除自己的评论
        async DeleteComment(Id) {
            await this.$PostDelete(`/Comment/Delete`, { Id: Id });
            this.GetCommentApi();
        },
        //点赞评论
        async Love(item) {
            if (CheckIsLogin()) {

                var { Success } = await this.$Post(`/CommentLove/CreateOrEdit`, {
                    RelativeId: this.$props.RelativeId,
                    Type: this.$props.Type,
                    CommentId: item.Id,
                    UserId: this.UserId,


                });
                this.formData = {};
                this.replyCommentShow = false;
                item.IsLove = true;
                this.$forceUpdate();
                this.$message.success("点赞成功")

            }
        },
        async CancelLove(item) {
            let { Data } = await this.$Post(`/CommentLove/Get`, { Id: 0, CommentId: item.Id, UserId: this.UserId });
            var { Success } = await this.$Post(`/CommentLove/Delete`, {
                Id: Data.Id
            });
            item.IsLove = false;
            this.$forceUpdate();
            this.$message.success("取消点赞成功")
        }


    },

};
</script>
<style scoped>
/* 评论区容器 */
.comment-container {
    /* 主容器样式 */

    margin: 0 auto;

}

/* 卡片基础样式 */
.Card {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
    padding: 20px;
    margin-bottom: 20px;
}

/* 评论输入区域 */
.comment-input-area {
    margin-bottom: 30px;
}

.comment-title {
    font-size: 20px;
    font-weight: 600;
    color: #333;
    margin-bottom: 20px;
}

.comment-form {
    /* 评论表单样式 */
    display: flex;
    flex-direction: column;
}

.comment-textarea {
    margin-bottom: 15px;
    border-radius: 4px;
}

.submit-btn {
    align-self: flex-end;
    padding: 10px 25px;
}

/* 评论列表区域 */
.comment-list-area {
    /* 评论列表容器样式 */
}

.comment-subtitle {
    font-size: 18px;
    color: #333;
    margin-bottom: 20px;
    display: flex;
    align-items: center;
}

.comment-count {
    font-size: 14px;
    color: #999;
    margin-left: 10px;
}

/* 评论项样式 */
.comment-item {
    padding: 20px 0;
    border-bottom: 1px solid #f0f0f0;
}

.comment-user {
    display: flex;
    align-items: center;
    margin-bottom: 12px;
}

.user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 12px;
}

.user-info {
    display: flex;
    flex-direction: column;
}

.user-name {
    font-size: 16px;
    font-weight: 500;
    color: #333;
}

.comment-time {
    font-size: 12px;
    color: #999;
    margin-top: 4px;
}

.comment-content {
    margin: 12px 0;
    font-size: 15px;
    line-height: 1.6;
    color: #333;
}

/* 评论操作区 */
.comment-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 12px;
}

.action-btn {
    display: inline-flex;
    align-items: center;
    padding: 6px 12px;
    color: #666;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s;
}

.action-btn:hover {
    color: var(--primary-color);
}

.action-btn i {
    margin-right: 4px;
}

.is-active {
    color: var(--primary-color);
}

.delete-btn:hover {
    color: #F56C6C;
}

/* 回复列表样式 */
.reply-list {
    margin-left: 52px;
    padding-left: 20px;
    border-left: 2px solid #f0f0f0;
}

.reply-item {
    padding: 15px 0;
}

/* 回复弹窗 */
.reply-dialog {
    .reply-textarea {
        margin-bottom: 20px;
    }

    .dialog-footer {
        text-align: right;
    }
}
</style>
