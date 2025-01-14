import { useQuery } from "react-query"
import api from '../api/api';

export const useFetchMyShortUrls = (token, onError) => {
    return useQuery("my-shortenurls"
        , async () => {
            const response = await api.get("/api/urls/myUrls", {
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "Authorization": `Bearer ${token}`,
                }
            });
            return response.data
        }
        , {
            select: (data) => {
                const sortedData = data.data.sort(
                    (a, b) => new Date(b.createdDate) - new Date(a.createdDate)
                );
                return sortedData;
            },
            onError,
            staleTime: 5000
        });
};

export const useFetchTotalClicks = (token, onError) => {
    return useQuery("url-totalclick"
        , async () => {
            const response = await api.get("/api/urls/totalClicks?startDate=2025-01-01&endDate=2025-12-31", {
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "Authorization": `Bearer ${token}`,
                }
            });
            return response.data
        }
        , {
            select: (data) => {
                const convertToArray = data.data.map((item) => ({
                    clickDate: item.clickDate,
                    count: item.count
                }));
                return convertToArray
            },
            onError,
            staleTime: 5000
        });
};