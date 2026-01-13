<template>
    <div>
        <div class="Card RightSideBar padding-xs">
            <div class="marign-top-xs line-bottom text-sm text-bold">它的文章</div>
            <el-divider></el-divider>
            <el-empty v-if="TopicList.length == 0" :image-size="200"></el-empty>
            <div v-if="TopicList.length > 0" class="pointer padding-xs" style="border-bottom: 1px solid gray;">
                <div v-for="(item, index) in TopicList" @click="ToTopic(item.Id)">
                    <div class="flex justify-between margin-bottom-xs text-lt">
                        <div>{{ item.Title }}</div>
                        <div>{{ item.ViewCount }}</div>
                    </div>
                </div>

            </div>

        </div>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex'
export default {
    name: "RecommendAuthorCard",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    props: {
        UserId: {

        },
        TopicId: {

        },
    },

    data() {
        return {
            TopicList: null,
        };
    },
    beforeCreate(e) {

    },
    created() {
        this.GetTopicList();
    },
    methods: {
        async ToTopic(Id) {
            this.$router.push({
                path: `/Client/TopicDetail`, query: {
                    TopicId: Id,

                }
            });
        },
        async GetTopicList() {
            let { Data: { Items } } = await this.$Post(`/Topic/List`, { CreatorId: this.UserId, AuditStatus: 1 });
            this.TopicList = Items;

        }
    },

};
</script>
<style scoped>
.Card {
    background: #fff;
    border-radius: 2px;
    -webkit-box-shadow: 0 1px 3px hsla(0, 0%, 7%, .1);
    box-shadow: 0 1px 3px hsla(0, 0%, 7%, .1);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin-bottom: 10px;
    overflow: hidden;
}

.RightSideBar {
    width: 296px;
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
    -webkit-line-clamp: 2;

    line-height: 1.2em;
}
</style>
