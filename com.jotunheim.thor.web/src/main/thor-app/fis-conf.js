fis.set('project.files', '/index.html'); // 按需编译。

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
    // parser: [
    //   fis.plugin('babel-6.x', {
    //     presets: ['es2015-loose', 'react', 'stage-2'],
    //     sourceMaps: true
    //   })
        //fis.plugin('translate-es3ify')
    //],
    rExt: '.js'
});

fis.match('*.less', {
    // fis-parser-less 插件进行解析
    parser: fis.plugin('less'),
    // .less 文件后缀构建后被改成 .css 文件
    rExt: '.css',
    postprocessor: fis.plugin('autoprefixer')
})

fis.match('::package', {
    // 本项目为纯前端项目，所以用 loader 编译器加载，
    // 如果用后端运行时框架，请不要使用。
    postpackager: fis.plugin('loader', {
        useInlineMap: true
    })
});

// 请用 fis3 release deploy 来启用。
fis.media('deploy')
    // 对 js 做 uglify 压缩。
    .match
    ('*.{js,jsx}', {
        optimizer: fis.plugin('uglify-js'),
        release:'/js/thor'
    })
    .match
    ('*.css',{
        optimizer: fis.plugin('clean-css'),
        useSptite:false,
        release:'/css/thor'
    })
    .match
    ('/images/**.png',{
        release:'/images/thor/$&'
    })
    .match
    ('index.html',{
        release:"WEB-INF/views/thor"
    })
    .match
    ('::package', {
        postpackager:fis.plugin
        ('loader',{
            allInOne:true
        }),
        // 更多用法请参考： https://github.com/fex-team/fis3-packager-deps-pack
        packager: fis.plugin
        ('deps-pack', {
            'pkg/index.js': /*当有多条时，请用数组*/
            [
            'modules/index.jsx',
            'modules/index.jsx:deps', // 以及其所有依赖
            ]
        })
    })
