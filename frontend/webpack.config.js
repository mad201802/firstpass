const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
    mode: "development",
    entry: "./app/src/index.js",
    devtool: "inline-source-map",
    target: "electron-renderer",
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader",
                    options: {
                        presets: [
                            [
                                "@babel/preset-env",
                                {
                                    targets: {
                                        esmodules: true
                                    }
                                }
                            ],
                            "@babel/preset-react"
                        ]
                    }
                }
            },
            {
                test: [/\.less$/i, /\.css$/i],
                use: [
                    // Creates `style` nodes from JS strings
                    "style-loader",
                    // Translates CSS into CommonJS
                    "css-loader",
                    // Compiles Less to CSS
                    "less-loader"
                ]
            },
            {
                test: /\.(jpg|jpeg|png|gif|mp3)$/,
                use: ["file-loader"]
            },
            {
                test: /\.svg$/i,
                issuer: /\.[jt]sx?$/,
                use: ["@svgr/webpack"]
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: "index.html",
            template: path.join(__dirname, "app/src", "index.html"),
        }),
    ],
    resolve: {
        extensions: [".js", ".jsx", ".json"],
    },
    output: {
        filename: "app.js",
        path: path.resolve(__dirname, "app", "build")
    }
};
