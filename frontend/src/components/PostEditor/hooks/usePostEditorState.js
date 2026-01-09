import { useEffect, useMemo, useState } from "react";
import { createAttachment, createImageBlock, createTextBlock } from "../utils/editorModel";
import { moveItem } from "../utils/reorder";

export default function usePostEditorState(options) {
  const initialTitle = (options && options.initialTitle) || "";
  const initialBlocks = (options && options.initialBlocks) || [createTextBlock("")];
  const initialAttachments = (options && options.initialAttachments) || [];

  const [title, setTitle] = useState(initialTitle);
  const [blocks, setBlocks] = useState(initialBlocks);
  const [attachments, setAttachments] = useState(initialAttachments);

  // ✅ 이미지 blob url 정리 (메모리 누수 방지)
  useEffect(() => {
    return () => {
      blocks.forEach((b) => {
        if (b.type === "image" && b.previewUrl) {
          try {
            URL.revokeObjectURL(b.previewUrl);
          } catch (e) {
            // ignore
          }
        }
      });
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const canSave = useMemo(() => {
    const hasTitle = title.trim().length > 0;
    const hasAnyContent = blocks.some((b) => {
      if (b.type === "text") return b.content.trim().length > 0;
      if (b.type === "image") return !!b.file;
      return false;
    });
    return hasTitle && hasAnyContent;
  }, [title, blocks]);

  // ---------- Blocks ----------
  const updateTextBlock = (blockId, nextContent) => {
    setBlocks((prev) =>
      prev.map((b) => (b.id === blockId ? { ...b, content: nextContent } : b))
    );
  };

  const addImageBlock = (file) => {
    if (!file) return;
    const previewUrl = URL.createObjectURL(file);
    const newBlock = createImageBlock(file, previewUrl);
    setBlocks((prev) => [...prev, newBlock]);
  };

  const removeBlock = (blockId) => {
    setBlocks((prev) => {
      const target = prev.find((b) => b.id === blockId);
      if (target && target.type === "image" && target.previewUrl) {
        try {
          URL.revokeObjectURL(target.previewUrl);
        } catch (e) {
          // ignore
        }
      }
      return prev.filter((b) => b.id !== blockId);
    });
  };

  const moveBlockUp = (blockId) => {
    setBlocks((prev) => {
      const idx = prev.findIndex((b) => b.id === blockId);
      return moveItem(prev, idx, idx - 1);
    });
  };

  const moveBlockDown = (blockId) => {
    setBlocks((prev) => {
      const idx = prev.findIndex((b) => b.id === blockId);
      return moveItem(prev, idx, idx + 1);
    });
  };

  // ---------- Attachments ----------
  const addAttachments = (files) => {
    if (!files || files.length === 0) return;
    const list = Array.from(files).map((f) => createAttachment(f));
    setAttachments((prev) => [...prev, ...list]);
  };

  const removeAttachment = (attachmentId) => {
    setAttachments((prev) => prev.filter((a) => a.id !== attachmentId));
  };

  const getPayload = () => {
    return {
      title: title.trim(),
      blocks: blocks.map((b) => {
        if (b.type === "text") return { id: b.id, type: b.type, content: b.content };
        if (b.type === "image") return { id: b.id, type: b.type, file: b.file };
        return b;
      }),
      attachments: attachments.map((a) => ({ id: a.id, file: a.file, name: a.name })),
    };
  };

  return {
    // state
    title,
    setTitle,
    blocks,
    attachments,
    canSave,

    // blocks actions
    updateTextBlock,
    addImageBlock,
    removeBlock,
    moveBlockUp,
    moveBlockDown,

    // attachment actions
    addAttachments,
    removeAttachment,

    // output
    getPayload,
  };
}
