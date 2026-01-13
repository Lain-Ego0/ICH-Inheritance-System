<template>
    <div class="main-container">
        <el-page-header class="card " @back="goBack" content="搜索结果">
        </el-page-header>
        <div class=" flex margin-top-xs">
            <div class="Topstory-mainColumn">
                <div class=" flex align-center  card">
                    <el-input placeholder="请输入你的关键字" prefix-icon="el-icon-search" v-model="KeyWord">
                    </el-input>
                    <el-button class="margin-left-xs" mini type="primary" icon="el-icon-search" @click="SearchTopic()">搜
                        索</el-button>
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
import RecommendAuthorListCard from "@/views/Front/RecommendAuthorListCard.vue"
import PaginationBox from "@/components/Pagination/PaginationBox.vue"
import TopicListCard from "@/views/Front/TopicListCard.vue"
import router from '@/router';
import { mapGetters } from 'vuex'
export default {
    components: { RecommendAuthorListCard, TopicListCard, PaginationBox },
    name: 'TopicSearch',
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
            KeyWord: '',
        };
    },
    watch: { // 监听，当路由发生变化的时候执行
        $route(to, from) {
            if (to.query.KeyWord != from.query.KeyWord) {
                this.where.KeyWord = to.query.KeyWord;
                this.$refs.PaginationBox.Reload(this.where);
            }
        },
    },
    created() {
        this.where.KeyWord = this.$route.query.KeyWord;
        this.KeyWord = this.$route.query.KeyWord;
    },
    mounted() {

    },
    methods: {
        SearchTopic() {
            this.$router.push({
                path: `/Front/TopicSearch`, query: {
                    KeyWord: this.KeyWord,
                }
            });
        },



    }


}
</script>

<style lang="scss" scoped>
.Topstory-mainColumn {
    -ms-flex-negative: 0;
    flex-shrink: 0;
    margin-bottom: 0;
    margin-right: 10px;
    flex: 1;
}
</style>
