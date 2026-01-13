<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>我的聊天消息</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="Topstory-container">
            <div class="Topstory-mainColumn">

                <div class="chat flex  ">
                    <div class="bar padding-xs">
                        <div>
                            <img :src="UserInfo.ImageUrls ? UserInfo.ImageUrls : require('@/assets/默认头像.png')"
                                style="width:40px;height: 40px;border-radius: 5px;">
                        </div>
                        <div class="margin-top-sm text-center text-xs"><i class="el-icon-message-solid "
                                :class="active == 0 ? 'text-green' : 'text-white'" @click="tabChange(0)"></i>
                        </div>
                        <!-- <div class="margin-top-sm text-center text-xs"><i class="el-icon-user-solid"
                                :class="active == 1 ? 'text-green' : 'text-white'" @click="tabChange(1)"></i></div> -->
                    </div>
                    <template v-if="active == 0">
                        <div class="chat-list" style="width: 250px;">
                            <div v-for="(item, index) in WechatCollectionList" :key="index" @click="SelectOtherUserChatBtn(item)" class="chat-item" :class="{ active: SelectChatFrined && SelectChatFrined.Id == item.OtherUserId }">
                                <img :src="item.OtherUserDto.ImageUrls ? item.OtherUserDto.ImageUrls : require('@/assets/默认头像.png')" class="contact-avatar">
                                <div class="contact-info">
                                    <div class="contact-name">{{ item.OtherUserDto.Name }}</div>
                                    <div class="last-message">{{ item.LastMessage }}</div>
                                </div>
                            </div>
                        </div>
                        <div class="chat-window flex-sub">
                            <div v-if="SelectChatFrined != null" class="chat-window-inner">
                                <div class="padding-xs solid-bottom chat-header">{{ SelectChatFrined.Name }}</div>
                                <div id="chat-content-list" class="chat-messages">
                                    <div v-for="(message, index) in MessageList" :key="index" class="message-item">
                                        <div v-if="UserId != message.SelfUserId" class="message-received">
                                            <img :src="message.SelfUserDto.ImageUrls ? message.SelfUserDto.ImageUrls : require('@/assets/默认头像.png')" class="message-avatar">
                                            <div class="message-bubble received">
                                                <div class="message-content">{{ message.Content }}</div>
                                                <div class="message-time">{{ message.SendTime }}</div>
                                            </div>
                                        </div>
                                        <div v-else class="message-sent">
                                            <div class="message-bubble sent">
                                                <div class="message-content">{{ message.Content }}</div>
                                                <div class="message-time">{{ message.SendTime }}</div>
                                            </div>
                                            <img :src="message.SelfUserDto.ImageUrls ? message.SelfUserDto.ImageUrls : require('@/assets/默认头像.png')" class="message-avatar">
                                        </div>
                                    </div>
                                </div>
                                <div class="chat-input solid-top">
                                    <el-input type="textarea" min="1" rows="3" max="1000" clearable autofocus
                                        resize="none" class="input-content" placeholder="请输入..."
                                        v-model="WaitSeedContent">
                                    </el-input>
                                    <div class="flex justify-end align-center padding-right-xs padding-bottom-xs margin-top-sm">
                                        <el-button class="" size="mini" @click="ClearContent()">清空</el-button>
                                        <el-button class="" type="primary" size="mini" :disabled="!WaitSeedContent"
                                            @click="SendMessageToOtherUser()">发送</el-button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </template>

                </div>


            </div>
            <div class="side">
                <div v-if="SelectChatFrined != null">

                </div>
            </div>

        </div>


    </div>
</template>

<script>


import { AddSocketMessageMonitor, RemoveSocketMessageMonitor, SendMessage } from '@/utils/webscoket';
import { mapGetters } from 'vuex';
export default {
    name: 'WeChat',
    computed: {
        ...mapGetters([
            "UserId", "UserInfo"
        ])
    },
    components: {

    },
    data() {
        let msgId = new Date().getTime();
        return {
            active: 0,
            WechatCollectionList: [],//好友列表

            MessageList: [],
            isConnectState: false,//连接状态
            SelectChatFrined: null,
            WaitSeedContent: "",//待发送的内容

            msgId: msgId,

        };
    },
    watch: {
        "active": function (n, o) {


        },
    },

    mounted() {
        this.InitSelectWechatUser();
        AddSocketMessageMonitor(this.msgId, this.OnMessage)
    },
    beforeDestroy() {
        RemoveSocketMessageMonitor(this.msgId)
    },
    methods: {
        //滚动到聊天底部
        scrollToElementBottom(element) {
            this.$nextTick(() => {
                element.scrollTop = element.scrollHeight;
            })

        },
        //得到消息返回
        async OnMessage(res) {
            console.log("OnMessage", res);
            if (res.Type == "Wechat_SendMessage_Back") {
                this.MessageList.push(res.Data);
                console.log(this.MessageList);
                this.scrollToElementBottom(document.getElementById("chat-content-list"));
            }
        },

        //初始化选择用户
        async InitSelectWechatUser() {
            await this.GetWechatCollectionListApi();
            if (this.$route.query.OtherUserId) {
                let item = this.WechatCollectionList.find(x => x.OtherUserId == this.$route.query.OtherUserId);
                this.SelectOtherUserChatBtn(item);
            }
        },
        //得到聊天列表
        async GetWechatCollectionListApi() {
            let { Data: { Items } } = await this.$Post("/WechatCollection/List", {
                SelfUserId: this.UserId
            }
            );
            this.WechatCollectionList = Items;

        },

        //选择和那个好友进行聊天
        async SelectOtherUserChatBtn(item) {
            this.SelectChatFrined = item.OtherUserDto;
            //得到2个之间的聊天记录
            let { Data: { Items } } = await this.$Post(`/WechatMessage/List`, {
                SelfUserId: item.SelfUserId,
                OtherUserId: item.OtherUserId,

            });
            this.MessageList = Items;

            this.scrollToElementBottom(document.getElementById("chat-content-list"));
        },
        //和某个好友进行消息发话
        async SendMessageToOtherUser() {
            let body = {
                SelfUserId: this.UserId,
                OtherUserId: this.SelectChatFrined.Id,
                Content: this.WaitSeedContent,
            };
            SendMessage("Wechat_SendMessage", body);
            this.$message.success("消息发送成功");
            this.WaitSeedContent = "";
        },
        async ClearContent() {
            this.WaitSeedContent = "";
        }


    }


}
</script>

<style scoped>
.Topstory-container {

    max-width: var(--content-width);
    margin: 20px auto;
}

.Card {
    background: #fff;
    border-radius: 2px;
    -webkit-box-shadow: 0 1px 3px hsla(0, 0%, 7%, .1);
    box-shadow: 0 1px 3px hsla(0, 0%, 7%, .1);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;

    overflow: hidden;
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

.RightSideBar {
    width: 296px;
    -webkit-align-self: normal;
    -ms-flex-item-align: normal;
    align-self: normal;
}

.CreatorEntrance {
    height: 52px;
    width: 100%;
    display: flex;
    align-items: center;
    padding: 10px;
}

.chat {
    width: 100%;
    height: 600px;
    background: #1a1a1a;
    border-radius: 8px;
    overflow: hidden;
    display: flex;
}

.bar {
    background-color: #2d2d2d;
    width: 60px;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 10px 0;
}

.chat-list {
    background-color: #2d2d2d;
    width: 250px;
    overflow-y: auto;
    border-right: 1px solid #404040;
}

.chat-list .active {
    background-color: #404040;
}

.chat-list .new_content {
    font-size: 13px;
    margin-top: 5px;
    color: #ccc;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.chat-window {
    background-color: #1a1a1a;
    flex: 1;
    display: flex;
    flex-direction: column;
}

.chat-window-inner {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.chat-window .chat-header {
    height: 50px;
    background: #2d2d2d;
    color: #fff;
    display: flex;
    align-items: center;
    padding: 0 15px;
    border-bottom: 1px solid #404040;
}

.chat-messages {
    flex: 1;
    overflow-y: auto;
    padding: 20px;
    background: #1a1a1a;
}

.message-item {
    margin-bottom: 20px;
    display: flex;
    align-items: flex-start;
}

.message-received {
    display: flex;
    align-items: flex-start;
    width: 100%;
}

.message-sent {
    display: flex;
    align-items: flex-start;
    justify-content: flex-end;
    width: 100%;
}

.message-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin: 0 10px;
}

.message-bubble {
    max-width: 60%;
    position: relative;
    margin-bottom: 5px;
}

.message-bubble.received {
    background: #404040;
    color: #fff;
    border-radius: 18px 18px 18px 4px;
    padding: 10px 15px;
}

.message-bubble.received::before {
    content: '';
    position: absolute;
    left: -8px;
    top: 10px;
    width: 0;
    height: 0;
    border: 8px solid transparent;
    border-right-color: #404040;
    border-left: 0;
}

.message-bubble.sent {
    background: #07c160;
    color: #fff;
    border-radius: 18px 18px 4px 18px;
    padding: 10px 15px;
}

.message-bubble.sent::after {
    content: '';
    position: absolute;
    right: -8px;
    top: 10px;
    width: 0;
    height: 0;
    border: 8px solid transparent;
    border-left-color: #07c160;
    border-right: 0;
}

.message-content {
    word-wrap: break-word;
    line-height: 1.4;
}

.message-time {
    font-size: 12px;
    color: #888;
    margin-top: 5px;
    text-align: right;
}

.chat-input {
    background: #2d2d2d;
    padding: 15px;
    border-top: 1px solid #404040;
}

.input-content {
    background: #404040;
    border: none;
    color: #fff;
}

.input-content .el-textarea__inner {
    background: #404040;
    border: none;
    color: #fff;
    resize: none;
}

.input-content .el-textarea__inner::placeholder {
    color: #888;
}

.el-button {
    background: #404040;
    border: none;
    color: #fff;
}

.el-button:hover {
    background: #555;
}

.el-button--primary {
    background: #07c160;
}

.chat-item {
    display: flex;
    align-items: center;
    padding: 15px;
    cursor: pointer;
    border-bottom: 1px solid #404040;
    transition: background 0.3s;
}

.chat-item:hover {
    background: #404040;
}

.chat-item.active {
    background: #404040;
}

.contact-avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 15px;
}

.contact-info {
    flex: 1;
}

.contact-name {
    color: #fff;
    font-weight: 500;
    margin-bottom: 5px;
}

.last-message {
    color: #ccc;
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
