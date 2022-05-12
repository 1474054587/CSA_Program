<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="UP昵称">
        <el-input
          v-model="teacher.name"
          style="width: 400px"
          v-if="teacher.notUpdate"
        />
        <el-input v-model="teacher.name" style="width: 400px" disabled v-else />
        &emsp;
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="search"
          v-text="text.search"
          v-if="teacher.notUpdate"
        />
        <el-button
          type="default"
          @click="resetData()"
          v-text="text.clear"
          v-if="teacher.notUpdate"
        />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="编程" />
          <el-option :value="2" label="其他" />
        </el-select>
        &emsp;
        <el-button type="primary" icon="el-icon-document" @click="saveOrUpdate"
          >保存</el-button
        >
      </el-form-item>
      <!-- <el-form-item label="mid">
        <el-input
          v-model="teacher.mid"
          style="width: 400px"
          v-if="teacher.notUpdate"
        />
        <el-input v-model="teacher.mid" style="width: 400px" disabled v-else />
        
      </el-form-item> -->
    </el-form>
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      v-if="teacher.notUpdate"
    >
      <el-table-column prop="uname" label="昵称" width="120" />

      <el-table-column prop="mid" label="mid" />

      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
            type="info"
            size="mini"
            icon="el-icon-search"
            @click="getUPInfo(scope.row.mid)"
            >详情</el-button
          >
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-circle-plus-outline"
            @click="addTeacher(scope.row)"
            >添加</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <video
      src="https://cn-cq-cmcc-v-05.bilivideo.com/upgcxcode/04/61/578666104/578666104_nb2-1-32.flv?e=ig8euxZM2rNcNbRVhwdVhwdlhWdVhwdVhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1650371648&gen=playurlv2&os=bcache&oi=3085307082&trid=0000053804fe9f054cd6b4e44639de7a0a87u&platform=pc&upsig=96295a7455ea1517efb4c8cda0f076ec&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&cdnid=6706&mid=0&bvc=vod&nettype=0&orderid=0,3&agrr=0&bw=96540&logo=80000000"
    ></video>
  </div>
</template>
<script>
import teacherApi from "@/api/edu/teacher";
import teacherInfo from "@/api/bilibili/up";
import searchApi from "@/api/bilibili/search";

export default {
  data() {
    return {
      list: [],
      userList: [10000],
      text: {
        clear: "清空",
        search: "查询",
      },
      teacher: {
        name: "",
        sort: 0,
        level: "",
        mid: "",
        notUpdate: true,
        avatar: "",
      },
    };
  },
  created() {
    //页面渲染之前执行
    this.init();
  },
  watch: {
    //监听
    $route(to, from) {
      //路由变化方式，路由发生变化，方法就会执行
      this.init();
    },
  },
  methods: {
    init() {
      //判断路径有id值,做修改
      if (this.$route.params && this.$route.params.id) {
        //从路径获取id值
        const id = this.$route.params.id;
        //调用根据id查询的方法
        this.getInfo(id);
      } else {
        //路径没有id值，做添加
        //清空表单
        this.teacher = {};
        this.teacher.notUpdate = true;
      }
    },
    //根据讲师id查询的方法
    getInfo(id) {
      teacherApi.getTeacherInfo(id).then((response) => {
        this.teacher = response.data.teacher;
        this.teacher.notUpdate = false;
      });
    },
    resetData() {
      this.init();
      this.list = null;
    },
    search() {
      searchApi
        .searchByType("bili_user", this.teacher.name)
        .then((response) => {
          this.list = JSON.parse(response.data.json).data.result;
        });
    },
    saveOrUpdate() {
      //判断修改还是添加
      //根据teacher是否有id
      if (this.teacher.level != 1 && this.teacher.level != 2) {
        this.$message({
          type: "warning",
          message: "分类为必选项",
        });
      } else if (!this.teacher.id) {
        //添加
        this.saveTeacher();
      } else {
        //修改
        this.updateTeacher();
      }
    },
    //修改讲师的方法
    updateTeacher() {
      teacherApi.updateTeacherInfo(this.teacher).then((response) => {
        //提示信息
        this.$message({
          type: "success",
          message: "修改成功!",
        });
        //回到列表页面 路由跳转
        this.$router.push({ path: "/teacher/table" });
      });
    },
    //添加讲师的方法
    saveTeacher() {
      teacherApi.addTeacher(this.teacher).then((response) => {
        //添加成功
        //提示信息
        this.$message({
          type: "success",
          message: "添加成功!",
        });
        //回到列表页面 路由跳转
        this.$router.push({ path: "/teacher/table" });
      });
    },
    addTeacher(data) {
      this.teacher.name = data.uname;
      this.teacher.mid = data.mid;
      this.teacher.avatar = data.upic;
      if (this.teacher.level != 1 && this.teacher.level != 2) {
        this.$message({
          type: "warning",
          message: "分类为必选项",
        });
      } else {
        teacherApi.addTeacher(this.teacher).then((response) => {
          this.$message({
            type: "success",
            message: "添加成功!",
          });
          this.$router.push({ path: "/teacher/table" });
        });
      }
    },
    getUPInfo(mid) {
      teacherInfo.getTeacherInfoByID(mid).then((response) => {
        this.showInfo(JSON.parse(response.data.json).data);
      });
    },
    showInfo(info) {
      var str =
        '<head><meta name="referrer" content="no-referrer"/> </head><body>' +
        "昵称：" +
        info.name +
        "<br/>" +
        "mid：" +
        info.mid +
        "<br/>" +
        "性别：" +
        info.sex +
        "<br/>" +
        "当前等级：" +
        info.level +
        "<br/>" +
        "签名：" +
        info.sign +
        "<br/>" +
        "头像：" +
        '<image src="' +
        info.face +
        '" width="100px" height="100px"/></body>';
      this.$alert(str, "", {
        dangerouslyUseHTMLString: true,
      });
    },
  },
};
</script>
