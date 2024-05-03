<!-- <template>
    <h1>앞면</h1>

    <RouterLink :to="{ name: 'pieceback' }">뒷면</RouterLink>
    <canvas id="myCanvas" width="284" height="464"></canvas>
    <button onclick="extractImage()">이미지 추출</button>
</template>

<script setup></script>

<style>
/* .box {
    width: 200px;
    height: 200px;
    clip-path: circle(50%);
} */

.masked {
    -webkit-mask-image: url('@/assets/testlayout.svg');
    mask-image: url('@/assets/testlayout.svg');
    width: 284px;
    height: 464px;
}
</style> -->
<template>
    <canvas id="myCanvas" width="284" height="464"></canvas>
    <button @click="extractImage">이미지 추출</button>
</template>

<script>
export default {
    name: 'ImageRenderer',
    mounted() {
        this.renderImage();
    },
    methods: {
        renderImage() {
            const canvas = document.getElementById('myCanvas');
            const ctx = canvas.getContext('2d');

            const img = new Image();
            img.crossOrigin = 'anonymous'; // CORS 에러 방지
            img.onload = () => {
                ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

                // SVG 요소 생성
                const svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
                svg.setAttribute('width', canvas.width);
                svg.setAttribute('height', canvas.height);
                svg.innerHTML = `<defs>
                              <clipPath id="clip-path">
                                <path d="M142,0C63.584,0,0,63.584,0,142s63.584,142,142,142s142-63.584,142-142S220.416,0,142,0z"/>
                              </clipPath>
                            </defs>
                            <image x="0" y="0" width="${canvas.width}" height="${canvas.height}" xlink:href="${img.src}" clip-path="url(#clip-path)"/>`;

                // Canvas에 SVG를 그림
                ctx.clearRect(0, 0, canvas.width, canvas.height);
                ctx.drawImage(svg, 0, 0);
            };
            img.src = 'https://i.namu.wiki/i/XL7e8b5czgrM9PZAg9kIfo-VAlM3fT3pv21f2xDo1X8fBTF_by7Z018n7n58LnXhHJ9FsGzOCtSJe6A9zQs2o7YVyDZs62kkQe9zEEH7zoEHV0vfAJ0QwCaCM4QZcZ2AeZ4QMy7EJcBOpRNWJXUxEw.webp';
        },
        extractImage() {
            const canvas = document.getElementById('myCanvas');
            const dataURL = canvas.toDataURL('image/png');
            console.log(dataURL);
            // 추출된 이미지 데이터를 활용할 수 있습니다.
        }
    }
}
</script>