export function moveItem(array, fromIndex, toIndex) {
    const next = [...array];
    if (fromIndex < 0 || fromIndex >= next.length) return next;
    if (toIndex < 0 || toIndex >= next.length) return next;
  
    const [item] = next.splice(fromIndex, 1);
    next.splice(toIndex, 0, item);
    return next;
  }
  