import { mapGetters } from "vuex";
const version = require("element-ui/package.json").version; // element-ui version from node_modules
const ORIGINAL_THEME = "#409EFF"; // default color
export default function () {
  return {
    data() {
      return {
        themeVal: ORIGINAL_THEME
      }
    },
    created() {
      this.themeVal = this.colorName;
    },
    watch: {
      themeVal(val, oldVal) {
        this.$store.commit("SET_COLOR_NAME", val);
        this.updateTheme(val, oldVal);
      }
    },
    computed: {
      ...mapGetters(["colorName"])
    },
    methods: {
      updateTheme(val, oldVal) {
        if (typeof val !== "string") return;
        const head = document.getElementsByTagName("head")[0];
        const themeCluster = this.getThemeCluster(val.replace("#", ""));
        const originalCluster = this.getThemeCluster(oldVal.replace("#", ""));
        const getHandler = (variable, id) => {
          return () => {
            const originalCluster = this.getThemeCluster(
              ORIGINAL_THEME.replace("#", "")
            );
            const newStyle = this.updateStyle(
              this[variable],
              originalCluster,
              themeCluster
            );

            let styleTag = document.getElementById(id);
            if (!styleTag) {
              styleTag = document.createElement("style");
              styleTag.setAttribute("id", id);
              head.appendChild(styleTag);
            }
            styleTag.innerText = newStyle;
          };
        };

        const chalkHandler = getHandler("chalk", "chalk-style");

        if (!this.chalk) {
          const url = `https://unpkg.com/element-ui@${version}/lib/theme-chalk/index.css`;
          this.getCSSString(url, chalkHandler, "chalk");
        } else {
          chalkHandler();
        }

        const link = [].slice.call(
          document.getElementsByTagName("head")[0].getElementsByTagName("link")
        );
        for (let i = 0; i < link.length; i++) {
          const style = link[i];
          if (style.href.includes('css')) {
            this.getCSSString(style.href, innerText => {
              const originalCluster = this.getThemeCluster(
                ORIGINAL_THEME.replace("#", "")
              );
              const newStyle = this.updateStyle(
                innerText,
                originalCluster,
                themeCluster
              );
              let styleTag = document.getElementById(i);
              if (!styleTag) {
                styleTag = document.createElement("style");
                styleTag.id = i;
                styleTag.innerText = newStyle;
                head.appendChild(styleTag);
              }
            });
          }
        }

        const styles = [].slice.call(document.querySelectorAll("style"))

        styles.forEach(style => {
          const {
            innerText
          } = style;
          if (typeof innerText !== "string") return;
          style.innerText = this.updateStyle(
            innerText,
            originalCluster,
            themeCluster
          );
        });
      },
      updateStyle(style, oldCluster, newCluster) {
        let newStyle = style;
        oldCluster.forEach((color, index) => {
          newStyle = newStyle.replace(new RegExp(color, "ig"), newCluster[index]);
        });
        return newStyle;
      },

      getCSSString(url, callback, variable) {
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
          if (xhr.readyState === 4 && xhr.status === 200) {
            if (variable) {
              this[variable] = xhr.responseText.replace(/@font-face{[^}]+}/, "");
            }
            callback(xhr.responseText);
          }
        };
        xhr.open("GET", url);
        xhr.send();
      },

      getThemeCluster(theme) {
        const tintColor = (color, tint) => {
          let red = parseInt(color.slice(0, 2), 16);
          let green = parseInt(color.slice(2, 4), 16);
          let blue = parseInt(color.slice(4, 6), 16);

          if (tint === 0) {
            // when primary color is in its rgb space
            return [red, green, blue].join(",");
          } else {
            red += Math.round(tint * (255 - red));
            green += Math.round(tint * (255 - green));
            blue += Math.round(tint * (255 - blue));

            red = red.toString(16);
            green = green.toString(16);
            blue = blue.toString(16);

            return `#${red}${green}${blue}`;
          }
        };

        const shadeColor = (color, shade) => {
          let red = parseInt(color.slice(0, 2), 16);
          let green = parseInt(color.slice(2, 4), 16);
          let blue = parseInt(color.slice(4, 6), 16);

          red = Math.round((1 - shade) * red);
          green = Math.round((1 - shade) * green);
          blue = Math.round((1 - shade) * blue);

          red = red.toString(16);
          green = green.toString(16);
          blue = blue.toString(16);

          return `#${red}${green}${blue}`;
        };

        const clusters = [theme];
        for (let i = 0; i <= 9; i++) {
          clusters.push(tintColor(theme, Number((i / 10).toFixed(2))));
        }
        clusters.push(shadeColor(theme, 0.1));
        return clusters;
      }
    }
  }
}