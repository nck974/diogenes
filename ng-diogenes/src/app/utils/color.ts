export function getColorOrDefault(color?: string) {
  if (color) {
    const hexColorRegex = /^(?:[0-9a-fA-F]{3}){1,2}$/;
    if (hexColorRegex.test(color)) {
      return `#${color}`;
    }
  }
  return "#ddd";

}