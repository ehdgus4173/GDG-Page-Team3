import usePostEditorState from "./hooks/usePostEditorState";
import TitleInput from "./inputs/TitleInput";
import BlockRenderer from "./blocks/BlockRenderer";
import ImagePickerButton from "./media/ImagePickerButton";
import AttachmentDropzone from "./attachments/AttachmentDropzone";
import AttachmentList from "./attachments/AttachmentList";
import PostEditorLayout from "./PostEditorLayout";

import "./PostEditorForm.css";

function PostEditorForm({ onSubmit, extraFields }) {
  const editor = usePostEditorState();

  const handleSave = () => {
    const payload = editor.getPayload();
    if (onSubmit) onSubmit(payload);
    else console.log(payload);
  };

  return (
    <PostEditorLayout onSave={handleSave} saveDisabled={!editor.canSave}>
      <div className="post-editor-body">
        <div className="post-editor-left">
          <ImagePickerButton onPick={editor.addImageBlock} />
        </div>

        <div className="post-editor-main">
          <TitleInput value={editor.title} onChange={editor.setTitle} />

          {extraFields ? <div className="post-editor-extra">{extraFields}</div> : null}

          <div className="post-editor-card">
            <BlockRenderer
              blocks={editor.blocks}
              onChangeText={editor.updateTextBlock}
              onMoveUp={editor.moveBlockUp}
              onMoveDown={editor.moveBlockDown}
              onRemove={editor.removeBlock}
            />
          </div>

          <div className="post-editor-attach">
            <div className="post-editor-attach-label caption">첨부파일</div>
            <AttachmentDropzone onFiles={editor.addAttachments} />
            <AttachmentList
              items={editor.attachments}
              onRemove={editor.removeAttachment}
            />
          </div>
        </div>
      </div>
    </PostEditorLayout>
  );
}

export default PostEditorForm;
