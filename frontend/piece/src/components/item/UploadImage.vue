<template>
    <div>
        <input type="file" @change="handleFileUpload" />
        <button @click="uploadImage">Upload Image</button>
    </div>
</template>

<script>
import { ref } from 'vue';
import { getPreSignedUrl } from '@/stores/fileupload';
import axios from 'axios';

export default {
    setup() {
        const file = ref(null);
        
        const handleFileUpload = event => {
            file.value = event.target.files[0];
        };
        
        const uploadImage = async () => {
            if (file.value) {
                const urlData = await getPreSignedUrl(file.value.name);
                const url = urlData.success;
                const formData = new FormData();
                formData.append('file', file.value);
                
                try {
                    const response = await axios.put(url, file.value, {
                        headers: {
                            'Content-Type': file.value.type,
                        },
                    });
            alert('Image uploaded successfully!');
        } catch (error) {
            console.error('Error uploading image', error);
            alert('Failed to upload image.');
        }
        }
    };
    
    return { handleFileUpload, uploadImage };
    },
};
</script>