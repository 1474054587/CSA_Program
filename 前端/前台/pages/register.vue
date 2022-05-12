<template>
  <div class="main">
    <div class="title">
      <a href="/login">登录</a>
      <span>·</span>
      <a class="active" href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="params">
        <el-form-item
          class="input-prepend restyle"
          prop="nickname"
          :rules="[
            { required: true, message: '请输入您的昵称', trigger: 'blur' },
          ]"
        >
          <div>
            <el-input
              type="text"
              placeholder="你的昵称"
              v-model="params.nickname"
            />
            <i class="iconfont icon-user" />
          </div>
        </el-form-item>

        <el-form-item
          class="input-prepend restyle no-radius"
          prop="mobile"
          :rules="[
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { validator: checkPhone, trigger: 'blur' },
          ]"
        >
          <div>
            <el-input type="text" placeholder="邮箱" v-model="params.mobile" />
            <!-- <i class="iconfont icon-phone" /> -->
            <i>
              <svg
                t="1651375686812"
                class="icon"
                viewBox="0 0 1024 624"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                p-id="2439"
                width="20"
                height="20"
              >
                <path
                  d="M838.954667 234.666667H170.666667c-3.626667 0-7.168 0.448-10.56 1.322666l323.690666 323.669334a21.333333 21.333333 0 0 0 30.165334 0L838.954667 234.666667z m46.144 14.186666l-260.693334 260.693334 262.933334 262.912c5.44-7.168 8.661333-16.106667 8.661333-25.792V277.333333c0-10.944-4.117333-20.906667-10.88-28.48zM843.861333 789.333333l-249.6-249.621333-50.133333 50.133333a64 64 0 0 1-90.517333 0l-50.112-50.133333L156.373333 786.88c4.48 1.578667 9.28 2.453333 14.314667 2.453333h673.194667zM128.661333 754.218667L373.333333 509.525333 129.578667 265.813333A42.709333 42.709333 0 0 0 128 277.333333v469.333334c0 2.56 0.213333 5.098667 0.661333 7.552zM170.666667 192h682.666666a85.333333 85.333333 0 0 1 85.333334 85.333333v469.333334a85.333333 85.333333 0 0 1-85.333334 85.333333H170.666667a85.333333 85.333333 0 0 1-85.333334-85.333333V277.333333a85.333333 85.333333 0 0 1 85.333334-85.333333z"
                  fill="#8a8a8a"
                  p-id="2440"
                ></path>
              </svg>
            </i>
          </div>
        </el-form-item>
        <el-form-item
          class="input-prepend restyle no-radius"
          prop="code"
          :rules="[
            { required: true, message: '请输入验证码', trigger: 'blur' },
          ]"
        >
          <div
            style="width: 100%; display: block; float: left; position: relative"
          >
            <el-input type="text" placeholder="验证码" v-model="params.code" />
            <!-- <i class="iconfont icon-phone" /> -->
            <i>
              <svg
                t="1651375686812"
                class="icon"
                viewBox="0 0 1024 624"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                p-id="2439"
                width="20"
                height="20"
              >
                <path
                  d="M838.954667 234.666667H170.666667c-3.626667 0-7.168 0.448-10.56 1.322666l323.690666 323.669334a21.333333 21.333333 0 0 0 30.165334 0L838.954667 234.666667z m46.144 14.186666l-260.693334 260.693334 262.933334 262.912c5.44-7.168 8.661333-16.106667 8.661333-25.792V277.333333c0-10.944-4.117333-20.906667-10.88-28.48zM843.861333 789.333333l-249.6-249.621333-50.133333 50.133333a64 64 0 0 1-90.517333 0l-50.112-50.133333L156.373333 786.88c4.48 1.578667 9.28 2.453333 14.314667 2.453333h673.194667zM128.661333 754.218667L373.333333 509.525333 129.578667 265.813333A42.709333 42.709333 0 0 0 128 277.333333v469.333334c0 2.56 0.213333 5.098667 0.661333 7.552zM170.666667 192h682.666666a85.333333 85.333333 0 0 1 85.333334 85.333333v469.333334a85.333333 85.333333 0 0 1-85.333334 85.333333H170.666667a85.333333 85.333333 0 0 1-85.333334-85.333333V277.333333a85.333333 85.333333 0 0 1 85.333334-85.333333z"
                  fill="#8a8a8a"
                  p-id="2440"
                ></path>
              </svg>
            </i>
          </div>
          <div
            class="btn"
            style="position: absolute; right: 0; top: 6px; width: 40%"
          >
            <a
              href="javascript:"
              type="button"
              @click="getCodeFun()"
              :value="codeTest"
              style="border: none; background-color: none"
              >{{ codeTest }}</a
            >
          </div>
        </el-form-item>

        <el-form-item
          class="input-prepend"
          prop="password"
          :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]"
        >
          <div>
            <el-input
              type="password"
              placeholder="设置密码"
              v-model="params.password"
            />
            <i class="iconfont icon-password" />
          </div>
        </el-form-item>

        <div class="btn">
          <input
            type="button"
            class="sign-up-button"
            value="注册"
            @click="submitRegister"
          />
        </div>
        <p class="sign-up-msg">
          点击 “注册” 即表示您同意并愿意遵守简书
          <br />
          <a target="_blank" href="http://www.jianshu.com/p/c44d171298ce"
            >用户协议</a
          >
          和
          <a target="_blank" href="http://www.jianshu.com/p/2ov8x3">隐私政策</a>
          。
        </p>
      </el-form>
      <!-- 更多注册方式 -->
      <div class="more-sign">
        <h6>社交帐号直接注册</h6>
        <ul>
          <li>
            <a id="weixin" class="weixin" target="_blank" @click="wxlogin"
              ><i class="iconfont icon-weixin"
            /></a>
            <!-- href="http://huaan.free.idcfengye.com/api/ucenter/wx/login"-->
          </li>
          <li>
            <a id="qq" class="qq" target="_blank" href="#"
              ><i class="iconfont icon-qq"
            /></a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import "~/assets/css/sign.css";
import "~/assets/css/iconfont.css";

import registerApi from "@/api/register";

export default {
  layout: "sign",
  data() {
    return {
      params: {
        //封装注册输入数据
        mobile: "",
        code: "", //验证码
        nickname: "",
        password: "",
      },
      sending: true, //是否发送验证码
      second: 60, //倒计时间
      codeTest: "获取验证码",
    };
  },
  methods: {
    wxlogin() {
      window.location.href = "http://localhost:8160/api/ucenter/wx/login";
    },
    //注册提交的方法
    submitRegister() {
      registerApi.registerMember(this.params).then((response) => {
        //提示注册成功
        this.$message({
          type: "success",
          message: "注册成功",
        });
        //跳转登录页面
        this.$router.push({ path: "/login" });
      });
    },
    timeDown() {
      let result = setInterval(() => {
        --this.second;
        this.codeTest = "(" + this.second + "秒)后可重新发送";
        if (this.second < 1) {
          clearInterval(result);
          this.sending = true;
          //this.disabled = false;
          this.second = 60;
          this.codeTest = "获取验证码";
        }
      }, 1000);
    },
    //通过输入手机号发送验证码
    getCodeFun() {
      registerApi.sendCode(this.params.mobile).then((response) => {
        this.sending = false;
        //调用倒计时的方法
        this.timeDown();
      });
    },

    checkPhone(rule, value, callback) {
      //debugger
      // if (!/^1[34578]\d{9}$/.test(value)) {
      //   return callback(new Error("手机号码格式不正确"));
      // }
      return callback();
    },
  },
};
</script>
