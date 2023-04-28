/**
 * 工作流统一分类格式
 * @param category 分类字典号
 * @returns {string}
 */
export function flowCategory(category) {
  return `flow_${category}`;
}

/**
 * 根据key获取流程路由
 * @param routes
 * @param key
 */
export function flowRoute(routes, key) {
  const data = routes.filter(d => {
    return d.routeKey === key;
  });
  return data.length === 0 ? [] : data[0].routeValue;
}
