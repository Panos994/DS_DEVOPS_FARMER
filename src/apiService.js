import axios from 'axios';

const API_BASE_URL = process.env.VUE_APP_API_BASE_URL || 'http://localhost:9090';

const apiService = {
        saveCooperative: async (cooperativeData, accessToken) => {
            const response = await axios.post(
                `${API_BASE_URL}/api/cooperative/new`,
                cooperativeData,
                {
                    headers: {
                        'Authorization': 'Bearer ' + accessToken,
                        'Content-Type': 'application/json',
                    },
                }
            );
            return response.data;
        },

    //os edo itan
    getCooperatives: async (accessToken) => {
        const response = await axios.get(`${API_BASE_URL}/api/cooperative`, {
            headers: {
                'Authorization': 'Bearer ' + accessToken,
            },
        });
        return response.data;
    },
    getCooperativeDetails: async (cooperativeId, accessToken) => {
        const response = await axios.get(`${API_BASE_URL}/api/cooperative/${cooperativeId}`, {
            headers: {
                'Authorization': 'Bearer ' + accessToken,
            },
        });
        return response.data;
    },
//edo
    // Add other API methods as needed
};

export default apiService;