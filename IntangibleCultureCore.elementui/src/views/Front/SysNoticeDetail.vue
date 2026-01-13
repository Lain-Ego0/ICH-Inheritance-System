<template>
    <div v-if="SysNoticeDetail != null">
        <div class=" ">

            <div class="card margin-top-xs SysNotice-detail">
                <div class="tit margin-bottom-lg">{{ SysNoticeDetail.Title }}</div>
                <div v-html="SysNoticeDetail.Content" class="rich_content"></div>
            </div>




        </div>

    </div>
</template>

<script>
import { mapGetters } from 'vuex';



import Pagination from "@/components/Pagination/PaginationBox.vue";

export default {
    name: "ShopGood",
    components: {
        Pagination: Pagination,

    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {
        return {
            SysNoticeDetail: null,
            where: {},
        }
    },
    watch: {
        '$route': function (to, from) {
            console.log('路由发生变化：', to.path);
            // 在这里可以执行你需要的操作，例如更新数据、发送请求等
            if (to.path == '/Front/SysNoticeDetail') {
                this.GetSysNoticeApi();

            }
        }
    },

    created() {

        this.GetSysNoticeApi();


    },
    methods: {



        async GetSysNoticeApi() {
            let { Data } = await this.$Post("/SysNotice/Get", { Id: this.$route.query.SysNoticeId })
            this.SysNoticeDetail = Data;
            this.$nextTick(() => {

            })
        },

        //返回上一个页面
        goBack() {
            this.$router.go(-1)
        },

    }

}
</script>

<style scope>
.tab {}

.tab .tit {
    font-weight: bolder;
}


.tit {
    font-weight: bolder;
    text-align: center;

}
</style>
