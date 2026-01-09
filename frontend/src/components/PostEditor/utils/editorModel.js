let _id = 0;
const uid = (prefix) => `${prefix}_${Date.now()}_${(_id += 1)}`;

export function createTextBlock(content = "") {
  return {
    id: uid("txt"),
    type: "text",
    content,
  };
}

export function createImageBlock(file, previewUrl) {
  return {
    id: uid("img"),
    type: "image",
    file,
    previewUrl,
  };
}

export function createAttachment(file) {
  return {
    id: uid("att"),
    file,
    name: file.name,
    size: file.size,
  };
}
