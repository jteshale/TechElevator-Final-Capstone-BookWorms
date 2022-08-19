<template>
  <div id="login" class="text-center">
    <div id="imagediv">

    <img class="background" src="pexels-janko-ferlic-590493.jpg">
    <div id="signinbackground">
    <form class="form-signin" @submit.prevent="login">
      <h2 class="h3 mb-3 font-weight-normal">Please Sign In</h2>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <label for="username" class="sr-only">Username: &nbsp;</label>
      <input
        type="text"
        id="username"
        class="form-control"
        v-model="user.username"
        required
        autofocus
      />
      <div id="loginpassword">
      <label for="password" id="passwordtxt" class="sr-only">Password: &nbsp;</label>
      <input
        type="password"
        id="password"
        class="form-control"
        v-model="user.password"
        required
      />
      </div>
      <div id="signinbtn">
      <button type="submit">Sign in</button>
      </div>
      <div id="registerlink">
      <router-link id="links" :to="{ name: 'register' }">Need an account?</router-link>
      </div>
    </form>
    </div>

    <h1>Bookworm</h1>
    <img id="logo" src="mini logo.png">
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>

.background{
  width: 100vw;
}

.form-signin{
  position:absolute;
  top: 30%;
  left: 8%;
  color: white;
  border: solid;
  border-color: white;
  border-width: 1px;
  border-radius: 3px;
  padding-right: 112px;
  padding-left: 10px;
  padding-top: 6px;
  padding-bottom: 10px;
  text-decoration: none;
  background-color: rgba(69, 31, 85, 0.5);
}

h2 {
  margin-top: 4%;
  margin-bottom: 4%;
  font-size: 3em;
  font-family: "Pangolin", sans-serif;
}


#links{
  text-decoration: none;
  color:white;
}

h1 {
  position:absolute;
  font-size: 3em;
  top: 23%;
  left: 13%;
  color:white;
  font-family: "Pangolin", sans-serif;
}

#signinbtn {
  margin-left: 31.5%;
}

#password {
  margin-left: 4px;
}

#registerlink {
  margin-left: 88px;
}

.sr-only {
  font-size: 1.2em;
}

#logo {
  position: absolute;
  top: 22%;
  left: 6.5%;
  height: 15vh;
}

</style>
