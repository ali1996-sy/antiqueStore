const STORE_NAME = "user";

const getUserSessionData = () => {
  const retrievedUser = sessionStorage.getItem(STORE_NAME);
  if (!retrievedUser) return;
  return JSON.parse(retrievedUser);
};
const getUserLocalData = () => {
  const retrievedUser = localStorage.getItem(STORE_NAME);
  if (!retrievedUser) return;
  return JSON.parse(retrievedUser);
};
const setUserSessionData = (user) => {
  const storageValue = JSON.stringify(user);
  sessionStorage.setItem(STORE_NAME, storageValue);
};
const setUserLocalData = (user) => {
  const storageValue = JSON.stringify(user);
  localStorage.setItem(STORE_NAME, storageValue);
};

const removeSessionData = () => {
  sessionStorage.removeItem(STORE_NAME);
};
const removeLocalData = () => {
  localStorage.removeItem(STORE_NAME);
};

export {
  getUserSessionData,
  setUserSessionData,
  removeSessionData,
  removeLocalData,
  getUserLocalData,
  setUserLocalData
};
