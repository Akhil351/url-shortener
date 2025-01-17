import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import Loader from "./Loader";

const ShortenUrlPage = () => {
  const { url } = useParams();

  useEffect(() => {
    if (url) {
      console.log("Redirecting to backend URL:", url);
      setTimeout(() => {
        window.location.href = `${import.meta.env.VITE_BACKEND_URL}/${url}`;
      }, 1000); 
    }
  }, [url]);

  return (
    <Loader/>
  )
};

export default ShortenUrlPage;
