async function request(path, options = {}) {
    const res = await fetch(path, {
      headers: {
        "Content-Type": "application/json",
        ...(options.headers || {}),
      },
      ...options,
    });
  
    if (!res.ok) {
      let reason = `HTTP ${res.status}`;
      try {
        const data = await res.json();
        reason = data.reason || data.message || reason;
      } catch (e) {}
      throw new Error(reason);
    }
  
    if (res.status === 204) return null;
    return res.json();
  }
  
  export const api = {
    get: (path) => request(path),
    post: (path, body) =>
      request(path, { method: "POST", body: JSON.stringify(body) }),
    put: (path, body) =>
      request(path, { method: "PUT", body: JSON.stringify(body) }),
    delete: (path) => request(path, { method: "DELETE" }),
  };
  