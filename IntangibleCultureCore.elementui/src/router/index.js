import store from "@/store";
import { getToken } from "@/utils/cache";
import AdminLayout from "@/views/Admin/Layout/index.vue";
import FrontLayout from "@/views/Front/Layout/index.vue";
import NProgress from "nprogress"; // progress bar
import "nprogress/nprogress.css"; // progress bar style
import Vue from "vue";
import VueRouter from "vue-router";
NProgress.inc(0.2);
NProgress.configure({
  easing: "ease",
  speed: 1000,
  showSpinner: false,
  trickle: false,
});
Vue.use(VueRouter);
const routes = [
  {
    path: "/",
    redirect: "/Front",
  },
  {
    path: "/Login",
    component: () => import("@/views/Login.vue"),
  },
  {
    path: "/Register",
    component: () => import("@/views/Register.vue"),
  },
  {
    path: "/ForgetPassword",
    component: () => import("@/views/ForgetPassword.vue"),
  },
];
//前台的路由
export const frontRouters = [
  {
    path: "/Front",
    redirect: "/Front/Home",
    component: FrontLayout,
    meta: {
      title: "前台",
      isAdmin: false,
    },
    children: [
      {
        path: "/Front/Home",
        meta: {
          title: "首页",
          isAdmin: false,
        },
        component: () => import("@/views/Front/Home"),
      },
      {
        path: "/Front/UserPerson",
        meta: {
          title: "我的个人信息",
          isAdmin: false,
        },
        component: () => import("@/views/Front/UserPerson"),
      },
      {
        path: "/Front/PasswordEdit",
        meta: {
          title: "修改密码",
          isAdmin: false,
        },
        component: () => import("@/views/Front/PasswordEdit"),
      },
      {
        path: "/Front/TopicSearch",
        meta: {
          title: "话题搜索",
          isAdmin: false,
        },
        component: () => import("@/views/Front/TopicSearch"),
      },
      {
        path: "/Front/TopicList",
        meta: {
          title: "话题交流",
          isAdmin: false,
        },
        component: () => import("@/views/Front/TopicList"),
      },
      {
        path: "/Front/MyTopicCollect",
        meta: {
          title: "话题收藏",
          isAdmin: false,
        },
        component: () => import("@/views/Front/MyTopicCollect"),
      },
      {
        path: "/Front/MyTopicCollect",
        meta: {
          title: "我的话题收藏",
          isAdmin: false,
        },
        component: () => import("@/views/Front/MyTopicCollect"),
      },
      {
        path: "/Front/TopicMy",
        meta: {
          title: "我的话题",
          isAdmin: false,
        },
        component: () => import("@/views/Front/TopicMy"),
      },
      {
        path: "/Front/TopicDetail",
        meta: {
          title: "话题详情",
          isAdmin: false,
        },
        component: () => import("@/views/Front/TopicDetail"),
      },
      {
        path: "/Front/TaHome",
        meta: {
          title: "作者首页",
          isAdmin: false,
        },
        component: () => import("@/views/Front/TaHome"),
      },
      {
        path: "/Front/SysNoticeDetail",
        meta: {
          title: "公告详情",
          isAdmin: false,
        },
        component: () => import("@/views/Front/SysNoticeDetail"),
      },
      {
        path: "/Front/SysNoticeList",
        meta: {
          title: "系统通知",
          isAdmin: false,
        },
        component: () => import("@/views/Front/SysNoticeList"),
      },
      {
        path: "/Front/Good",
        meta: {
          title: "文创品详情",
        },
        component: () => import("@/views/Front/Good"),
      },
      {
        path: "/Front/BuyCard",
        meta: {
          title: "购物车",
        },
        component: () => import("@/views/Front/BuyCard"),
      },
      {
        path: "/Front/MyUserAddress",
        meta: {
          title: "我的收货地址",
        },
        component: () => import("@/views/Front/MyUserAddress"),
      },
      {
        path: "/Front/ToOrder",
        meta: {
          title: "去下单",
        },
        component: () => import("@/views/Front/ToOrder"),
      },
      {
        path: "/Front/MyOrderList",
        meta: {
          title: "我的订单",
        },
        component: () => import("@/views/Front/MyOrderList"),
      },
      {
        path: "/Front/OrderExpressQuery",
        meta: {
          title: "物流查询",
        },
        component: () => import("@/views/Front/OrderExpressQuery"),
      },
      {
        path: "/Front/OrderComment",
        meta: {
          title: "订单评论",
        },
        component: () => import("@/views/Front/OrderComment"),
      },
      {
        path: "/Front/OrderReturn",
        meta: {
          title: "订单退款售后",
        },
        component: () => import("@/views/Front/OrderReturn"),
      },
      {
        path: "/Front/ShopGood",
        meta: {
          title: "店铺文创品",
        },
        component: () => import("@/views/Front/ShopGood"),
      },
      {
        path: "/Front/GoodCollect",
        meta: {
          title: "收藏的文创品",
        },
        component: () => import("@/views/Front/GoodCollect"),
      },

      {
        path: "/Front/GoodTypeList",
        meta: {
          title: "文创品分类",
        },
        component: () => import("@/views/Front/GoodTypeList"),
      },
      {
        path: "/Front/GoodSearchResult",
        meta: {
          title: "文创品搜索结果",
        },
        component: () => import("@/views/Front/GoodSearchResult"),
      },
      {
        path: "/Front/PersonCenter",
        meta: {
          title: "个人中心",
        },
        component: () => import("@/views/Front/PersonCenter"),
      },
      {
        path: "/Front/GoodList",
        meta: {
          title: "文创品列表",
        },
        component: () => import("@/views/Front/GoodList"),
      },
      {
        path: "/Front/WeChat",
        meta: {
          title: "聊天",
        },
        component: () => import("@/views/Front/WeChat"),
      },
      {
        path: "/Front/CulturalList",
        meta: {
          title: "非遗文化",
        },
        component: () => import("@/views/Front/CulturalList"),
      },
      {
        path: "/Front/CulturalDetail",
        meta: {
          title: "非遗文化详情",
        },
        component: () => import("@/views/Front/CulturalDetail"),
      },
      {
        path: "/Front/InheritorList",
        meta: {
          title: "传承人",
        },
        component: () => import("@/views/Front/InheritorList"),
      },
      {
        path: "/Front/InheritorDetail",
        meta: {
          title: "传承人详情",
        },
        component: () => import("@/views/Front/InheritorDetail"),
      },
    ],
  },
];

export const adminRouters = [
  {
    path: "/Admin",
    redirect: "/Admin/Home",
    component: AdminLayout,
    meta: {
      title: "控制台",
      isAdmin: true,
    },
    children: [
      {
        path: "/Admin/Home",
        meta: {
          isAdmin: true,
        },
        component: () => import("@/views/Admin/Home"),
      },
      {
        path: "/Admin/UserList",
        meta: {
          title: "用户信息",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/UserList"),
      },
      {
        path: "/Admin/UserPerson",
        meta: {
          title: "个人信息",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/UserPerson"),
      },
      {
        path: "/Admin/PasswordEdit",
        meta: {
          title: "修改密码",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/PasswordEdit"),
      },
      {
        path: "/Admin/TopicList",
        meta: {
          title: "话题",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/TopicList"),
      },
      {
        path: "/Admin/TopicTypeList",
        meta: {
          title: "话题类型",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/TopicTypeList"),
      },
      {
        path: "/Admin/TopicAnalyseEchart",
        meta: {
          title: "话题统计",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/TopicAnalyseEchart"),
      },
      {
        path: "/Admin/TopicSynthesizeAnalyse",
        meta: {
          title: "话题综合分析",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/TopicSynthesizeAnalyse"),
      },
      {
        path: "/Admin/SysNoticeList",
        meta: {
          title: "系统公告",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/SysNoticeList"),
      },

      {
        path: "/Admin/GoodStockList",
        meta: {
          title: "库存记录",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/GoodStockList"),
      },
      {
        path: "/Admin/ShopEdit",
        meta: {
          title: "店铺编辑",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/ShopEdit"),
      },

      {
        path: "/Admin/GoodTypeList",
        meta: {
          title: "文创品分类",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/GoodTypeList"),
      },
      {
        path: "/Admin/GoodList",
        meta: {
          title: "文创品",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/GoodList"),
      },
      {
        path: "/Admin/GoodPropList",
        meta: {
          title: "文创品属性",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/GoodPropList"),
      },
      {
        path: "/Admin/OrderInfoList",
        meta: {
          title: "订单管理",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/OrderInfoList"),
      },
      {
        path: "/Admin/OrderReturnList",
        meta: {
          title: "订单退款",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/OrderReturnList"),
      },

      {
        path: "/Admin/GoodReplenishmentList",
        meta: {
          title: "库存预警",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/GoodReplenishmentList"),
      },
      {
        path: "/Admin/WeChat",
        meta: {
          title: "聊天",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/WeChat"),
      },
      {
        path: "/Admin/CulturalList",
        meta: {
          title: "非遗文化",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/CulturalList"),
      },

      {
        path: "/Admin/CulturalTypeList",
        meta: {
          title: "非遗文化类别",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/CulturalTypeList"),
      },

      {
        path: "/Admin/InheritorList",
        meta: {
          title: "传人",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/InheritorList"),
      },
      {
        path: "/Admin/SynthesizeAnalyse",
        meta: {
          title: "综合分析",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/SynthesizeAnalyse"),
      },
    ],
  },
];

const router = new VueRouter({
  routes: [...routes, ...frontRouters, ...adminRouters], // (缩写) 相当于 routes: routes
});

// 编写导航路由
router.beforeEach(async (to, from, next) => {
  NProgress.start();
  const hasToken = getToken();

  if (hasToken) {
    if (store.getters.HasUserInfo == false) {
      await store.dispatch("GetInfo");
      if (store.getters.UserId == 0 || store.getters.UserId == null) {
        store.dispatch("Logout");
      }
    }
    //如果去往的页面是后台
    if (to.meta && to.meta.isAdmin == true) {
      if (store.getters.RoleType == "管理员") {
        next();
      } else {
        next({ path: "/" });
      }
    } else if (to.meta && to.meta.isAdmin == false) {
      if (store.getters.RoleType == "管理员") {
        next({ path: "/Admin" });
      } else {
        next();
      }
    } else {
      next();
    }
  } else {
    if (to.meta && to.meta.isAdmin) {
      next({ path: "/Login" });
    } else {
      next();
    }
  }
  NProgress.done();
});
router.afterEach(() => {
  // finish progress bar
  window.scrollTo({ top: 0 });
  NProgress.done();
});

export default router;
