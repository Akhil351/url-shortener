import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const ShortenUrlPage = () => {
  const { url } = useParams();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (url) {
      console.log("Redirecting to backend URL:", url);
      setTimeout(() => {
        window.location.href = `${import.meta.env.VITE_BACKEND_URL}/${url}`;
      }, 1000); 
    }
  }, [url]);

  return null
};

export default ShortenUrlPage;
