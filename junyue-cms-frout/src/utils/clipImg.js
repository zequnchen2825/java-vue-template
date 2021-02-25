/**
 * @desc 对图片处理圆角
 * @param {Number} width 图片宽度
 * @param {Number} height 图片的高度
 * @param {Number} radius 圆角的半径
 * @param {Number} leftTopRadius 左上角的圆角半径
 * @param {Number} leftBottomRadius 左下角的圆角半径
 * @param {Number} rightTopRadius 右上角的圆角半径
 * @param {Number} rightBottomRadius 右下角的圆角半径
 */
class ClipImg {
  // 初始化生成的画布
  __canvas = null;
  // 在画布上填充的图片
  __img = null;
  file = null;
  constructor(
    width,
    height,
    radius,
    leftTopRadius = radius,
    leftBottomRadius = radius,
    rightTopRadius = radius,
    rightBottomRadius = radius
  ) {
    if (!width || !height) {
      throw "必须传入图片的尺寸大小";
    }
    this.width = width;
    this.height = height;
    this.radius = radius;
    this.leftTopRadius = leftTopRadius;
    this.leftBottomRadius = leftBottomRadius;
    this.rightTopRadius = rightTopRadius;
    this.rightBottomRadius = rightBottomRadius;
  }
  /**
   * @desc 初始化画布
   * @param {File} img 图片文件
   */
  async formatImg(img) {
    this.__img = img;
    const canvas = document.querySelector("#clipImg");
    if (canvas) {
      document.body.removeChild(canvas);
    } else {
      this.__canvas = document.createElement("canvas");
      this.__canvas.id = "clipImg";
      this.__canvas.width = this.width;
      this.__canvas.height = this.height;
      this.__canvas.style.visibility = 'hidden'
      document.body.appendChild(this.__canvas);
    }
    // 检测下是否支持canvas
    if (this.__canvas.getContext("2d")) {
      const context = this.__canvas.getContext("2d");
      await this.__strokeRoundRect(context);
      // 根据画的圆角图片做裁剪
      context.clip();
      const file = await this.__drawImg(context);
      if (file) {
        document.body.removeChild(this.__canvas)
        return file;
      }
    } else {
      throw "您当前的浏览器不支持canvas, 请换个浏览器再进行操作";
    }
  }
  __strokeRoundRect(cxt) {
    return new Promise((resolve, reject) => {
      if (
        this.leftTopRadius * 2 > this.width ||
        this.leftTopRadius * 2 > this.height ||
        this.leftBottomRadius * 2 > this.height ||
        this.leftBottomRadius * 2 > this.height ||
        this.rightTopRadius * 2 > this.width ||
        this.rightTopRadius * 2 > this.height ||
        this.rightBottomRadius * 2 > this.width ||
        this.rightBottomRadius * 2 > this.height
      ) {
        reject("设置的圆角尺寸过大, 圆角大小 * 2 不能超过图片的快或高");
      } else {
        cxt.save();
        this.__drawRoundRectPath(cxt);
        cxt.strokeStyle = "transparent";
        cxt.stroke();
        cxt.restore();
        resolve();
      }
    });
  }
  // 画带圆角的图形
  __drawRoundRectPath(cxt) {
    cxt.beginPath();
    // 从右下角开始绘制
    cxt.arc(
      this.width - this.rightBottomRadius,
      this.height - this.rightBottomRadius,
      this.rightBottomRadius,
      0,
      Math.PI / 2
    );

    // 矩形下边
    cxt.lineTo(this.rightBottomRadius, this.height);

    // 左下角的圆角
    cxt.arc(
      this.leftBottomRadius,
      this.height - this.leftBottomRadius,
      this.leftBottomRadius,
      Math.PI / 2,
      Math.PI
    );

    // 矩形左边
    cxt.lineTo(0, this.leftTopRadius);

    // 左上角的圆角
    cxt.arc(
      this.leftTopRadius,
      this.leftTopRadius,
      this.leftTopRadius,
      Math.PI,
      (Math.PI / 2) * 3
    );

    // 矩形上边
    cxt.lineTo(this.width - this.rightTopRadius, 0);

    // 右上角的圆角
    cxt.arc(
      this.width - this.rightTopRadius,
      this.rightTopRadius,
      this.rightTopRadius,
      (Math.PI / 2) * 3,
      Math.PI * 2
    );

    // 矩形右边
    cxt.lineTo(this.width, this.height - this.rightBottomRadius);

    cxt.closePath();
  }
  // 向画布中填充图片并导出圆角图片
  __drawImg(cxt) {
    return new Promise((resolve, reject) => {
      const fileReader = new FileReader();
      fileReader.onload = event => {
        const img = new Image();
        img.onload = async () => {
          cxt.drawImage(img, 0, 0, this.width, this.height);
          const canvaImg = this.__canvas.toDataURL("image/png", 1.0);
          const file = this.__base64toFile(canvaImg);
          resolve(file);
        };
        img.src = event.target.result;
      };
      fileReader.readAsDataURL(this.__img);
    });
  }
  // 将canvas导出的base64 图片构造成可供上传的file 格式
  __base64toFile(base64) {
    const binary = atob(base64.split(",")[1]);
    const mime = base64.split(",")[0].match(/:(.*?);/)[1];
    const array = [];
    for (let i = 0; i < binary.length; i++) {
      array.push(binary.charCodeAt(i));
    }

    const fileData = new Blob([new Uint8Array(array)], {
      type: mime
    });

    const file = new File([fileData], `${new Date().getTime()}.png`, {
      type: mime
    });
    return file;
  }
}

export default ClipImg;
