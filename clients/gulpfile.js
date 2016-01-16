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
  // proxy: {
  //   //target: 'http://localhost:8080'
  //   target: 'http://192.168.99.100:8080'
  // }
  proxy: {
    uses : 'gulp/multiproxy.js'
  }
};


/*
  Init basebuild-angular
 */
require('basebuild-angular')(basebuildOptions);