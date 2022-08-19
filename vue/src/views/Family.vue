<template>
  <div>
    <img class="homebackground" src="living-room.jpg" />
    <div class="home">
      <p>Welcome, {{ this.username }}!</p>
    </div>
    <div class="nav">
      <p>
        <router-link class="profilelink" v-bind:to="{ name: 'profile' }"
          >Profile</router-link
        >
      </p>
      <router-link class="familylink" :to="{ name: 'family' }"
        >Family</router-link
      >
      <p>
        <router-link class="readinglistlink" :to="{ name: 'readingList' }"
          >Reading List</router-link
        >
      </p>
      <p>
        <router-link class="prizelink" :to="{ name: 'prizes' }">
          Prizes</router-link></p>
    </div>
    <div class="familywindow">
      <div class="headline">
      <h3 v-show="familyName == ''">Please register your family...</h3>
      <h3 v-show="familyName != ''">The {{ family.familyName }} family</h3>
      </div>
      <!-- <div class="familymembers"
          v-for="member in this.$store.state.familyMembers"
          v-bind:key="member.user_id">
          <p>{{ member.username + familyReading }}</p>
      </div> -->
      <reading-info v-for="member in this.$store.state.familyMembers"
      v-bind:key="member.user_id" v-bind:curUser="member"/>
      <div v-if="!isRegistered">
        <p id="RegisterFamily">Register your family!</p>
        <input
          type="text"
          placeholder="Family Name"
          v-model="family.familyName"
          v-show="disabled"
        />
        <input
          type="submit"
          v-on:click.prevent="registerFamily(), (disabled = false)"
          v-show="disabled"
        />
      </div>

      <div>
        <p id="AddChild">Add a child account</p>
        <form>
          <input
            type="text"
            placeholder="Child Username"
            v-model="child.username"
          />
          <br />
          <input type="text" placeholder="Password" v-model="child.password" />
          <br />
          <input
            type="text"
            placeholder="Confirm Password"
            v-model="child.confirmPassword"
          />
          <input type="submit" v-on:click.prevent="registerChild()" />
        </form>
      </div>

      <div>
        <p id="AddFamily">Add a family member</p>
        <form>
          <input
            type="text"
            placeholder="Child Username"
            v-model="newFamilyMember.username"
          />
          <!-- <input type="text" placeholder="ID of Family" v-model="newFamilyMember.family_id"> -->
          <input type="submit" v-on:click.prevent="addFamilyMember()" />
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import FamilyService from "../services/FamilyService";
import ReadingInfo from "../components/ReadingInfo.vue"

export default {
  name: "the-family",
  components: {
    ReadingInfo
  },
  data() {
    return {
      familyReading: "",
      registered: false,
      username: this.$store.state.user.username,
      family: {
        familyName: ""
      },
      child: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "ROLE_USER",
      },
      disabled: true,
      newFamilyMember: {
        username: "",
      },
    };
  },
  props: ["id"],

  created() {
    FamilyService.getFamilyName(this.$store.state.user).then((response) => {
      this.family.familyName = response.data;
        FamilyService.list(this.$store.state.user).then((response) => {
          this.$store.state.familyMembers = response.data;
        });
      }
    ),

     FamilyService.getReadingActivity(this.$store.state.familyMembers).then((response) => {
        this.familyReading = response.data;
      })
  },

  computed:  {
    isRegistered() {
     if (this.$store.state.familyMembers.length != 0) {
      return true} else {
        return false
      }
  }},

  methods: {
    registerChild() {
      FamilyService.registerChild(this.child);
      this.child.username = "";
      this.child.password = "";
      this.child.confirmPassword = "";
        },
  

    registerFamily() {
      FamilyService.registerFamily(this.family).then((response) => {
        if (response.status === 201) {
          this.$store.state.familyMembers = response.data;
          this.registered = true;
        }
      });
    },

    addFamilyMember() {
      FamilyService.addToFamilyAccount(this.newFamilyMember).then(
        (response) => {
          if (response.status === 201) {
            this.$router.push("/");
          }
        }
      )
    }
  }
};
</script>

<style>


#RegisterFamily,
#AddChild,
#AddFamily {
   color: #f8c630;
   font-size: 20px;
}

.familywindow {
  text-align: center;
  position: absolute;
  top: 50px;
  left: 18%;
  background: rgba(114, 78, 145, 0.7);
  height: 70vh;
  width: 70vw;
}

h3 {
  font-size: 40px;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
}

.familymembers {
    height:40px;
    width: 50%;
    background: rgba(255, 253, 253, 0.7);
    position:relative;
    margin: 0 auto;
    color:rgb(131, 126, 126);
    font-size: 20px;
    font-family: "Mouse Memoirs", sans-serif;
}

.familylink, .prizelink {
  color: #f8c630;
}
</style>