import { useQuery } from "react-query"
import api from '../api/api';
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