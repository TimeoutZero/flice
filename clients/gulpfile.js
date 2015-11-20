/**
 *  Welcome to your gulpfile!
 *  The gulp tasks are splitted in several files in the gulp directory
 *  because putting all here was really too long
 */

'use strict';

var gulp = require('gulp');

/**
 *  Default task clean temporaries directories and launch the
 *  main optimization build task
 */
gulp.task('default', ['clean'], function () {
  gulp.start('build');
});


/*
  Options
 */
var basebuildOptions = {
  mainAngularModule: 'web'
};

basebuildOptions.modulesData = {
  proxy: {
    target: 'http://docker:8081'
  }
};

/*
  Init basebuild-angular
 */
require('basebuild-angular')(basebuildOptions);