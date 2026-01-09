import "./NoticeAttachments.css";

function NoticeAttachments({ files }) {
  return (
    <section className="notice-attachments">
      <p className="notice-attachments-label caption">첨부파일</p>

      <div className="notice-attachments-list">
        {files.map((file, idx) => (
          <a
            key={`${file.url}-${idx}`}
            className="notice-file"
            href={file.url}
            download
            target="_blank"
            rel="noreferrer"
          >
            <div className="notice-file-left">
              <span className="notice-file-icon" aria-hidden="true">📄</span>
              <span className="notice-file-name body-1">{file.name}</span>
            </div>

            <span className="notice-file-download" aria-hidden="true">⭳</span>
          </a>
        ))}
      </div>
    </section>
  );
}

export default NoticeAttachments;
