<template>
    <div>
        <div class="page-container margin-top-xs card">
            <el-page-header @back="goBack()" content="物流详情">
            </el-page-header>
            <div class="margin-top-xs">
                <el-timeline>
                    <el-timeline-item :timestamp="item.time | YMDHMSFormat" placement="top"
                        v-for="(item, index) in ExpressList" :key="index">
                        <el-card>

                            <p>{{ item.desc }}</p>
                        </el-card>
                    </el-timeline-item>

                </el-timeline>
            </div>

        </div>
    </div>

</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: "OrderInfoList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {

        return {
            ExpressList: []

        };
    },
    created() {
        this.QueryExpressApi();
    },
    methods: {
        //查看物流
        async QueryExpressApi(item) {
            let { Data } = await this.$Post("/OrderInfo/QueryExpress", { Id: this.$route.query.Id, LogisticsNo: this.$route.query.LogisticsNo })
            console.log(JSON.parse(Data))
            let logisticsData = JSON.parse(Data);
            this.ExpressList = logisticsData.data.logisticsTraceDetailList;
        },
        //返回上一个页面
        goBack() {
            this.$router.go(-1)
        }





    },
};
</script>