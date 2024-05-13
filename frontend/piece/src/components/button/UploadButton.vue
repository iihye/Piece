<template>
    <button @click="uploadImage">사진 올리기</button>
    <input type="file" ref="fileInput" @change="handleFileSelected" style="display: none" />
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

// 이벤트를 정의하고 내보내는 부분
const emit = defineEmits(['uploadSuccess', 'uploadError']);

// 파일 입력 참조
const fileInput = ref(null);

// 버튼 클릭 시 input을 트리거하는 함수
function uploadImage() {
    fileInput.value.click();
}

// 파일이 선택되었을 때 실행될 함수
async function handleFileSelected(event) {
    const file = event.target.files[0];
    if (!file) {
        emit('uploadError', 'No file selected');
        return;
    }

    try {
        const presignedURL = await axios.get(`http://localhost:8000/api/piece/upload/${encodeURIComponent(file.name)}`);
        console.log('file is : ', file);

        const URL = presignedURL.data.data;
        console.log('url is : ', URL);
        const uploadFile = await axios.put(URL, file, {
            headers: { 'Content-Type': file.type }
        });

        if (uploadFile.status === 200 || uploadFile.status === 204) {
            emit('uploadSuccess', 'Image uploaded successfully');
        } else {
            emit('uploadError', 'Failed to upload image');
        }
    } catch (error) {
        emit('uploadError', error.message);
    }
}
</script>

<!-- <template>
    <button @click="uploadImage">사진 올리기</button>
    <input type="file" ref="fileInput" @change="handleFileSelected" style="display: none" />
  </template>




  
<script>
import axios from 'axios';
export default {
    data() {
        return {
        file: null,
        imageUrl: '',
        };
    },
    methods: {
        handleFileUpload(event) {
        this.file = event.target.files[0];
        },
        async uploadImage() {
        // 1. 백엔드에게 get 요청을 보내서 url을 응답받기
        try {
            const response = await axios.get(`http://localhost:8000/api/piece/upload/${this.file.name}`);
            this.imageUrl = response.data.url;
            console.log("url is : ", url);
        } catch (error) {
            console.error('Error getting pre-signed URL:', error);
            return;
        }

        // 2. 응답받은 url에 put 요청으로 이미지 업로드하기
        try {
            await axios.put(this.imageUrl, this.file, {
                headers: {
                    'Content-Type': this.file.type,
                    },
                });
                console.log('이미지 업로드 성공');
        } catch (error) {
            console.error('이미지 업로드 실패 ㅜ', error);
            }
        },
    },
};
</script> -->

  
  <!-- <script setup>
  import { ref } from 'vue';
  import axios from 'axios';
  
  const emit = defineEmits(['uploadSuccess', 'uploadError']);
  const fileInput = ref(null);
  
  function uploadImage() {
    fileInput.value.click();
  }
  
  async function handleFileSelected(event) {
    const file = event.target.files[0];
    if (!file) {
      emit('uploadError', 'No file selected');
      return;
    }
  
    try {
      const response = await axios.get(`http://localhost:8000/api/piece/upload/${encodeURIComponent(file.name)}`);
      const presignedURL = response.data.data;
      console.log('Presigned URL:', presignedURL);
    
// 이제 이 인터셉터를 포함한 상태로 요청을 보냄
    const uploadResponse = await axios.put(presignedURL, file, {
    headers: {
        'Content-Type': file.type
    }
});

    //   const uploadResponse = await axios.put(presignedURL, file.data, {
    //     headers: { 'Content-Type': file.type },
    //     onUploadProgress: progressEvent => console.log(progressEvent.loaded)  // 진행 상황을 로깅
    //   });
      
      if (uploadResponse.status === 200 || uploadResponse.status === 204) {
        emit('uploadSuccess', 'Image uploaded successfully');
      } else {
        emit('uploadError', 'Failed to upload image. Status: ' + uploadResponse.status);
      }
    } catch (error) {
      console.error('Upload error:', error);
      emit('uploadError', error.message);
    }
  }
  </script> -->
  
  <!-- <template>
    <div>
      <button @click="uploadImage">사진 올리기</button>
      <input type="file" ref="fileInput" @change="handleFileSelected" style="display: none" />
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    emits: ['uploadSuccess', 'uploadError'],  // 컴포넌트에서 발생하는 이벤트 선언
    data() {
      return {
        file: null,  // 선택된 파일을 저장할 데이터
      };
    },
    methods: {
      uploadImage() {
        this.$refs.fileInput.click();  // 파일 입력 필드를 트리거
      },
      async handleFileSelected(event) {
        this.file = event.target.files[0];  // 선택된 파일을 저장
        if (!this.file) {
          this.$emit('uploadError', 'No file selected');  // 파일이 없으면 에러 이벤트 발생
          return;
        }
  
        try {
          // 백엔드로부터 Pre-Signed URL을 요청
          const response = await axios.get(`http://localhost:8000/api/piece/upload/${encodeURIComponent(this.file.name)}`);
          console.log(`${encodeURIComponent(this.file.name)}`);
          const presignedURL = response.data.data;  // 응답에서 URL 추출
          console.log('Presigned URL is: ', presignedURL);
  
          // 받은 URL로 파일 업로드 요청
          const uploadResponse = await axios.put(presignedURL, this.file, {
            headers: { 'Content-Type': this.file.type }  // 필요한 경우 적절한 Content-Type 설정
          });
  
          if (uploadResponse.status === 200 || uploadResponse.status === 204) {
            this.$emit('uploadSuccess', 'Image uploaded successfully');  // 업로드 성공 이벤트 발생
          } else {
            this.$emit('uploadError', 'Failed to upload image');  // 실패한 경우 에러 이벤트 발생
          }
        } catch (error) {
          console.error('이미지 업로드 실패', error);
          this.$emit('uploadError', error.message);  // 네트워크 오류 처리
        }
      }
    }
  };
  </script>
   -->

<!-- <template>
    <div>
        <button @click="uploadImage">사진 올리기</button>
        <input type="file" ref="fileInput" @change="handleFileSelected" style="display: none" />
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const fileInput = ref(null);
const emit = defineEmits(['uploadSuccess', 'uploadError']);

const uploadImage = () => {
    fileInput.value.click();
};

const handleFileSelected = async (event) => {
    const formData = new FormData();
    const file = event.target.files[0];
    formData.append("profile", file);


    if (!file) {
        emit('uploadError', 'No file selected');
        return;
    }

    try {
        const response = await axios.get(`http://localhost:8000/api/piece/upload/${encodeURIComponent(file.name)}`);
        const presignedURL = response.data.data; // 가정: 응답 데이터 구조에 따라 조정 필요
        console.log('Presigned URL is:', presignedURL);

    //   const uploadResponse = await axios.put(presignedURL, file, {
    //     headers: { 'Content-Type': file.type }
    //   });

    const uploadResponse = await axios.put(presignedURL, file, {
        headers: { 'Content-Type': "multipart/form-data" , },
        data: formData,
        });

    if (uploadResponse.status === 200 || uploadResponse.status === 204) {
        emit('uploadSuccess', 'Image uploaded successfully');
    } else {
        emit('uploadError', 'Failed to upload image');
    }
    } catch (error) {
        console.error('Upload failed:', error);
        emit('uploadError', error.message);
        }
    };
</script> -->

<!-- <template>
    <div>
        <button @click="uploadImage">사진 올리기</button>
        <input type="file" ref="fileInput" @change="handleFileSelected" style="display: none" />
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
// import { Buffer } from 'buffer';

const fileInput = ref(null);
const emit = defineEmits(['uploadSuccess', 'uploadError']);

const uploadImage = () => {
    fileInput.value.click();
};

const handleFileSelected = async (event) => {
    const file = event.target.files[0];

    if (!file) {
        emit('uploadError', 'No file selected');
        return;
    }

    try {
        const response = await axios.get(`http://localhost:8000/api/piece/upload/${encodeURIComponent(file.name)}`);
        const presignedURL = response.data.data;
        console.log('presignedURL is ', presignedURL);

        const uploadResponse = await axios.put(presignedURL, file, {
            headers: { 'Content-Type': file.type },
        });

        console.log(uploadResponse.data);
        if (uploadResponse.status === 200 || uploadResponse.status === 204) {
            emit('uploadSuccess', 'Image uploaded successfully');
        } else {
            emit('uploadError', 'Failed to upload image');
        }
    } catch (error) {
        console.error('Upload failed:', error);
        emit('uploadError', error.message);
    }
};
</script> -->

<!-- <template>
    <div>
        <button @click="uploadImage">사진 올리기</button>
        <input type="file" ref="fileInput" @change="handleFileSelected" style="display: none" />
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const fileInput = ref(null);
const emit = defineEmits(['uploadSuccess', 'uploadError']);

// 이미지 업로드 트리거 함수
const uploadImage = () => {
    fileInput.value.click();
};

// 파일 선택 핸들러
const handleFileSelected = async (event) => {
    const file = event.target.files[0];

    // 파일이 선택되지 않은 경우
    if (!file) {
        console.log('파일이 선택되지 않았음.');
        emit('uploadError', 'No file selected');
        return;
    }

    try {
        // Pre-Signed URL 받아오기
        const response = await axios.get(`http://localhost:8000/api/piece/upload/${encodeURIComponent(file.name)}`);
        const presignedURL = response.data.data;
        console.log('Presigned URL is:', presignedURL);

        const uploadResponse = await axios.put(presignedURL, file, {
            headers: { 'Content-Type': file.type },
        });

        console.log('uploadResponse.data: ', uploadResponse.data);
        if (uploadResponse.status === 200 || uploadResponse.status === 204) {
            emit('uploadSuccess', 'Image uploaded successfully');
        } else {
            emit('uploadError', 'Failed to upload image');
        }
    } catch (error) {
        console.error('Upload failed:', error);
        emit('uploadError', error.message);
    }
};
</script> -->


<!-- <template>
    <div>
      <input type="file" @change="handleFileUpload" />
      <button :disabled="!selectedFile" @click="uploadFile">Upload File</button>
      <p v-if="uploadStatus">{{ uploadStatus }}</p>
    </div>
  </template>
  
  <script>
  import { ref } from 'vue';
  import axios from 'axios';
  
  export default {
    name: 'UploadFile',
    setup() {
      const selectedFile = ref(null);
      const uploadStatus = ref('');
  
      const handleFileUpload = event => {
        selectedFile.value = event.target.files[0];
        uploadStatus.value = '';
      };
  
      const uploadFile = async () => {
        if (!selectedFile.value) {
          alert('Please select a file first!');
          return;
        }
        const fileName = encodeURIComponent(selectedFile.value.name);
        try {
          // GET 요청으로 Pre-signed URL 받아오기
          const { data } = await axios.get(`your-backend-endpoint/${fileName}`);
          const presignedUrl = data.url;
          
          // PUT 요청으로 파일 업로드
          const options = {
            headers: {
              'Content-Type': selectedFile.value.type
            }
          };
          await axios.put(presignedUrl, selectedFile.value, options);
          uploadStatus.value = 'File uploaded successfully!';
        } catch (error) {
          console.error('Error uploading file:', error);
          uploadStatus.value = 'Failed to upload file.';
        }
      };
  
      return { handleFileUpload, uploadFile, uploadStatus };
    }
  };
  </script>
   -->
   <!-- <template>
    <div>
      <button @click="selectFile">Upload File</button>
      <p v-if="uploadStatus">{{ uploadStatus }}</p>
    </div>
  </template>
  
  <script>
  import { ref } from 'vue';
  import axios from 'axios';
  
  export default {
    name: 'UploadButton',
    setup() {
      const uploadStatus = ref('');
  
      const selectFile = async () => {
        const input = document.createElement('input');
        input.type = 'file';
        input.onchange = async (event) => {
          const file = event.target.files[0];
          if (!file) return;
  
          const fileName = encodeURIComponent(file.name);
          uploadStatus.value = 'Uploading...';
          
          try {
            // GET 요청으로 Pre-signed URL 받아오기
            const { data } = await axios.get(`http://localhost:8000/api/piece/upload/${fileName}`);
            const presignedUrl = data.data;
            console.log('url is ', presignedUrl);
            
            console.log('file is ', file);
            console.log('file.type is', file.type);

            // PUT 요청으로 파일 업로드
            const options = {
              headers: {
                'Content-Type': file.type
              }
            };

            await axios.put(presignedUrl, file, options);
            uploadStatus.value = 'File uploaded successfully!';
          } catch (error) {
            console.error('Error uploading file:', error);
            uploadStatus.value = 'Failed to upload file.';
          }
        };
        input.click();
      };
  
      return { selectFile, uploadStatus };
    }
  };
  </script>
   -->

<!-- <template>
    <div>
        <button @click="selectFile">Upload File</button>
        <p v-if="uploadStatus">{{ uploadStatus }}</p>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const fileInput = ref(null);
const uploadStatus = ref('');

const selectFile = async () => {
    const input = document.createElement('input');
    input.type = 'file';
    input.onchange = async (event) => {
        const file = event.target.files[0];
        if (!file) {
            console.log('No file selected');
            uploadStatus.value = 'No file selected';
            return;
            }

        uploadStatus.value = 'Uploading...';

        try {
            const response = await axios.get(`http://localhost:8000/api/piece/upload/${encodeURIComponent(file.name)}`);
            const presignedUrl = response.data.data;

            await axios.put(presignedUrl, file, {
            headers: {
                'Content-Type': file.type
                }
            });

            uploadStatus.value = 'File uploaded successfully!';
        } catch (error) {
            console.error('Error uploading file:', error);
            uploadStatus.value = 'Failed to upload file.';
        }
    };
    input.click();
};
</script> -->