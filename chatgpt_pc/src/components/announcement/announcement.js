import Vue from "vue";

import announcement from "./announcement.vue";

let PromptConstructor = Vue.extend(announcement);
let instance;

const announcementVue = function(options = {}) {
    instance = new PromptConstructor({
        data: options,
    });
    document.body.appendChild(instance.$mount().$el);
};

export default announcementVue;