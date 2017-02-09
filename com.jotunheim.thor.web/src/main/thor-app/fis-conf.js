fis.match('*.{js,css,png}', {
  useHash: false
});

// 设置成是模块化 js
fis.match('/{node_modules,modules}/**.{js,jsx}', {
    isMod: true
});

// 采用 commonjs 模块化方案。
fis.hook('commonjs', {
    baseUrl: './modules'
});
// 改用 npm 方案，而不是用 fis-components
fis.unhook('components');
fis.hook('node_modules', {
    'shimProcess': false
})

fis.match('*.{js,es,es6,jsx,ts,tsx}', {
    preprocessor: fis.plugin('js-require-css')
})

fis.match('{/modules/**.js,*.jsx}', {
    //parser: fis.plugin('typescript'),
    //typescript 编译速度会很快，但是对一些 es7 的语法不支持，可以用 babel 来解决。用以下内容换掉 typescript 的parser配置就好了。
    parser: fis.plugin('babel-5.x', {
        sourceMaps: true,
        optional: ["es7.decorators", "es7.classProperties"],
        loose:true
    }),
    rExt: '.js'
});

fis.match('*.less', {
    // fis-parser-less 插件进行解析
    parser: fis.plugin('less'),
    // .less 文件后缀构建后被改成 .css 文件
    rExt: '.css',
    postprocessor: fis.plugin('autoprefixer')
});

fis.match('{*.log,*.sh,package.json}', {
    release: false
});

fis.match ('*.{js,jsx,js.map}', { // 对 js 做 uglify 压缩。
    optimizer: fis.plugin('uglify-js'),
    release: '/js/thor/$&'
}).match ('*.{css,less}',{
    // fis-optimizer-clean-css 插件进行压缩，已内置
    optimizer: fis.plugin('clean-css'),
    useSptite:false,
    release: '/css/thor/$&'
}).match ('/images/**.png',{
    // fis-optimizer-png-compressor 插件进行压缩，已内置
    optimizer: fis.plugin('png-compressor'),
    release: '/images/thor/$&'
}).match ('index.html', {
    release: '/html/index.html'
});

fis.match ('{/static/**.js,/node_modules/**}', {
    release: '/js/3rd/$&'
});

fis.match ('::package', {
    // 本项目为纯前端项目，所以用 loader 编译器加载，
    // 如果用后端运行时框架，请不要使用。
    postpackager:fis.plugin
    ('loader',{
        allInOne:false,
        useInlineMap: false
    }),
    // 更多用法请参考： https://github.com/fex-team/fis3-packager-deps-pack
    packager: fis.plugin
    ('deps-pack', {
        'index.js': // 当有多条时，请用数组
        [
        'static/mod.js',
        'modules/index.jsx',
        'modules/index.jsx:deps', // 以及其所有依赖
        ]
    })

});


// 请用 fis3 release debug 来启用。
fis.media('debug').match('*.{js,css,less,png}', {
    useHash: false,
    useSprite: false,
    optimizer: null
});

