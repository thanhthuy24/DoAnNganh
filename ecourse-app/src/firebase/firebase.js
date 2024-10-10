// Import the functions you need from the SDKs you need
import APIs, { endpoints } from '@/configs/APIs.js';
import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
// import { useStore } from "vuex";

const firebaseConfig = {
  apiKey: "AIzaSyBQdCWs9Ayh80mGECtUhmW4UkICtG-eyc0",
  authDomain: "notification-25866.firebaseapp.com",
  projectId: "notification-25866",
  storageBucket: "notification-25866.appspot.com",
  messagingSenderId: "514040359923",
  appId: "1:514040359923:web:1dafa707ea9505ec994086",
  measurementId: "G-9S008P4CFW"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

// export { messaging, getToken, onMessage };
export const requestFCMToken = async () => {
    try {
      const currentToken = await getToken(messaging, { vapidKey: 'BByp-sNmfCH1_hHe6wvTdYioeCY43FiR8UHoZ09e2cprmNII4FYV0KcrP7PYJ9Cz9lH8huTDNAodunWSidX3O1Q' });
      if (currentToken) {
        console.log('FCM Token:', currentToken);
        return currentToken;  // Lấy FCM token để gửi về server
      } else {
        console.warn('No registration token available. Request permission to generate one.');
        return null;
      }
    } catch (error) {
      console.error('An error occurred while retrieving token. ', error);
      return null;
    }
  };

  export const handleLogin = async (user, yourAuthToken) => {
    const token = await requestFCMToken(); // Lấy FCM token
    if (token) {
      // Gửi FCM Token lên backend để lưu trữ
      await APIs.post(endpoints.token, {
        token: token, user_id: user.id
      },{
        headers: {
          Authorization: `Bearer ${yourAuthToken}`,
      },
       
      });
      console.log("done");
    }
  };
  
  // Lắng nghe thông báo khi ứng dụng đang mở
  export const onMessageListener = () => {
    onMessage(messaging, (payload) => {
      console.log('Message received: ', payload);
      toast("Your register have been approved!! Please check now!!", {
				"theme": "auto",
				"type": "success","autoClose": 3000,
				"dangerouslyHTMLString": true
				})
    });
  };