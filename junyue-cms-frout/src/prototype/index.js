import Vue from 'vue';

import {hasRole, hasAnyRole, hasPermission} from '../utils/auth';

Vue.prototype.$hasPermission = hasPermission;
Vue.prototype.$hasRole = hasRole;
Vue.prototype.$hasAnyRole = hasAnyRole;


