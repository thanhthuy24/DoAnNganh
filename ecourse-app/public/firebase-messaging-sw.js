// public/firebase-messaging-sw.js

// eslint-disable-next-line no-undef
importScripts('https://www.gstatic.com/firebasejs/9.0.0/firebase-app-compat.js');
// eslint-disable-next-line no-undef
importScripts('https://www.gstatic.com/firebasejs/9.0.0/firebase-messaging-compat.js');

const firebaseConfig =  {
  apiKey: "AIzaSyBQdCWs9Ayh80mGECtUhmW4UkICtG-eyc0",
  authDomain: "notification-25866.firebaseapp.com",
  projectId: "notification-25866",
  storageBucket: "notification-25866.appspot.com",
  messagingSenderId: "514040359923",
  appId: "1:514040359923:web:1dafa707ea9505ec994086",
  measurementId: "G-9S008P4CFW"
};

// eslint-disable-next-line no-undef
firebase.initializeApp(firebaseConfig);

// eslint-disable-next-line no-undef
const messaging = firebase.messaging();

messaging.onBackgroundMessage(function (payload) {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
    icon: '/firebase-logo.png' // Bạn có thể thay đổi icon nếu muốn
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});
