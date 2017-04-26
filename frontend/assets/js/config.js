/* ============================================================
 * File: config.js
 * Configure routing
 * ============================================================ */

var translationsEN = {
    LOGIN:{
        HEADLINE: "Sign into your pages account",
        FORM:{
            USERNAME:       "Login",
            ERROR1:         "This field is required.",
            PASSWORD:       "Password",
            ERROR2_TITLE:   "Incorrect login",
            ERROR2:         "Username or password is invalid",
            ERROR3_TITLE:   "Confirm your account",
            ERROR3:         "To access your account you need confirm your account in your email",
            ERROR4_TITLE:   "Your account is banned",
            ERROR4:         "Your account is banned, contact us for clarification"
        },
        RECOVER:    "Recover Account",
        SIGNED:     "Keep Me Signed in",
        LOG_IN:     "Log in",
        FOOTER1:    "Create a pages account.",
        FOOTER2:    "Images Displayed are solely for representation purposes only, All work copyright of respective owner, otherwise © 2017 Music Social.",
        SIGN_UP:    "Sign up",  
        REGISTER:   "New to Music Social ?"
    },
    RECOVER:{
        HEADLINE: "Recover your account",
        SUBTITLE: "Fill in your email below to receive the account recovery link.",
        FORM:{
            EMAIL:          "Email",
            ERROR1:         "Enter a valid email.",
            SUCCESS1_TITLE: "Right email",
            SUCCESS1:       "Password recovery sent to the informed email.",
            ERROR2_TITLE:   "Wrong email",
            ERROR2:         "Email not found."  
        },
        HELP: "Help? Contact Support",
        SEND: "Send"
    },
    RESET:{
        HEADLINE: "Set your new password",
        FORM:{
            PASSWORD: "Password",
            PH_PASSWORD: "Minimum of 4 Characters",
            ERROR1: "This field is required.",
            ERROR2: "Password should have at least 4 characters",
            CONF_PASSWORD: "Confirm Password",
            ERROR3: "Password must be equals"            
        },
        HELP: "Help? Contact Support",
        SEND: "Send"
    },
    REGISTER:{
        HEADLINE:   "Music Social makes it easy to enjoy what matters the most in your life!",
        SUBTITLE:   "Create your Music Social account. It's easy and fast!",
        FORM:{
            NAME:           "Name",
            ERROR1:         "This field is required.",
            USERNAME:       "Username",
            BIRTHDAY:       "Birthday",
            CITY:           "City",
            ERROR2:         "Enter a valid city.",
            STATE:          "State",
            ERROR3:         "Enter a valid state.",
            COUNTRY:        "Country",  
            ERROR4:         "Enter a valid country.",
            EMAIL:          "Email",
            ERROR5:         "Enter a valid email.",
            PASSWORD:       "Password",
            PH_PASSWORD:    "Minimum of 6 characters",
            ERROR6:         "Password should have at least 6 characters",
            CONF_PASSWORD:  "Confirm Password" ,
            ERROR7:         "Password must be equals",
            ERROR8:         "Password should have at maximum 12 characters",
            ERROR9:         "Only letters allowed.",
            NOTIF1_TITLE:   "Success",
            NOTIF1:         "Successful registration! An account confirmation will be sent to the informed email.",
            ERROR10_TITLE:  "Incorrect registration",
            ERROR10:        "Already exists register for the informed email.",
            ERROR11:        "Start Date should not be less than current date"
        },
        HELP: "Help? Contact Support",
        SEND: "Create a new account"
    },
    HEADER:{
        NOTIFICATIONS1: "Read all notifications",
        SEARCH1: "Type anywhere to search",
        USER_INFO: {
            SETTINGS:   "Settings",
            FEEDBACK:   "Feedback",
            HELP:       "Help",
            LOGOUT:     "Logout"
        }
    },
    FOOTER:{
        TEXT1:  "All rights reserved.",
        TERMS:  "Terms of use",
        POLICY: "Privacy Policy",
        CRAFTED:"Hand-crafted",
        MADE:   "&amp; Made with Love ®"
    }
};

var translationsBR = {
    LOGIN_HEADLINE:   "Conecte-se em sua conta",
    LOGIN2:{
        USERNAME:     "Usuário",
        ERROR:        "Este campo é obrigatório.",
        PASSWORD:     "Senha"
    },
    LOGIN_RECOVER:    "Recuperar a conta",
    LOGIN_SIGNED:     "Mantenha-me conectado",
    LOGIN_LOG_IN:     "Entrar",
    LOGIN_FOOTER1:    "Crie uma conta.",
    LOGIN_FOOTER2:    "As imagens exibidas são exclusivamente para fins de representação. © 2017 Music Social.",
    LOGIN_SIGN_UP:    "Inscrever-se",  
    LOGIN_REGISTER:   "Novo em Music Social?"
};

angular.module('app')
    .config(['$translateProvider', function ($translateProvider) {
        //utilizado para garantir a segurança
        $translateProvider.useSanitizeValueStrategy('escaped');
        $translateProvider
        .translations('en', translationsEN)
        .translations('br', translationsBR)
        .preferredLanguage('en');
    }])
    .config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider',

        function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {
            $urlRouterProvider
                .otherwise('/app/dashboard');

            $stateProvider

                .state('app', {
                    abstract: true,
                    url: "/app",
                    controller: 'HeaderCtrl',
                    data: {
                        requireLogin: true,
                        requireAdmin: false
                    },
                    templateUrl: "tpl/app.html"
                })
                
                .state('app.dashboard', {
                    url: "/dashboard",
                    templateUrl: "tpl/dashboard.html",
                    controller: 'DashboardCtrl',
                    
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'nvd3',
                                    'mapplic',
                                    'rickshaw',
                                    'metrojs',
                                    'sparkline',
                                    'skycons',
                                    'switchery'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load([
                                        'assets/js/controllers/dashboard.js'
                                    ]);
                                });
                        }]
                    }
                })

            // Email app
            .state('app.email', {
                    abstract: true,
                    url: '/email',
                    templateUrl: 'tpl/apps/email/email.html',
                    data: {
                        requireLogin: false,
                        requireAdmin: false
                    },
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'menuclipper',
                                    'wysihtml5'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load([
                                        'assets/js/apps/email/service.js',
                                        'assets/js/apps/email/email.js'
                                    ])
                                });
                        }]
                    }
                })
            .state('app.email.inbox', {
                url: '/inbox/:emailId',
                templateUrl: 'tpl/apps/email/email_inbox.html'
            })
            .state('app.email.compose', {
                url: '/compose',
                templateUrl: 'tpl/apps/email/email_compose.html'
            })
            // Social app
            .state('app.social', {
                url: '/social',
                data: {
                        requireLogin: false
                },
                templateUrl: 'tpl/apps/social/social.html',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                                'isotope',
                                'stepsForm'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load([
                                    'pages/js/pages.social.min.js',
                                    'assets/js/apps/social/social.js'
                                ])
                            });
                    }]
                }
            })
            .state('app.feed', {
                url: '/feed',
                data: {
                        requireLogin: false
                },
                templateUrl: 'tpl/apps/social/feed.html',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                                'isotope',
                                'stepsForm'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load([
                                    'pages/js/pages.social.min.js',
                                    'assets/js/apps/social/feed.js'
                                ])
                            });
                    }]
                }
            })
            
            //Calendar app
            .state('app.calendar', {
                url: '/calendar',
                templateUrl: 'tpl/apps/calendar/calendar.html',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                                'switchery',
                                'moment-locales',
                                'interact'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load([
                                    'pages/js/pages.calendar.min.js',
                                    'assets/js/apps/calendar/calendar.js'
                                ])
                            });
                    }]
                }
            })
            .state('app.builder', {
                url: '/builder',
                template: '<div></div>',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            'assets/js/controllers/builder.js',
                        ]);
                    }]
                }
            })

            .state('app.layouts', {
                url: '/layouts',
                template: '<div ui-view></div>'
            })
            .state('app.layouts.default', {
                url: '/default',
                templateUrl: 'tpl/layouts_default.html'
            })
            .state('app.layouts.secondary', {
                url: '/secondary',
                templateUrl: 'tpl/layouts_secondary.html'
            })
            .state('app.layouts.horizontal', {
                url: '/horizontal',
                templateUrl: 'tpl/layouts_horizontal.html'
            })
            .state('app.layouts.rtl', {
                url: '/rtl',
                controller: 'RTLCtrl',
                templateUrl: 'tpl/layouts_default.html',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            'assets/js/controllers/rtl.js',
                        ]);
                    }]
                }
            })
            .state('app.layouts.columns', {
                url: '/columns',
                templateUrl: 'tpl/layouts_columns.html'
            })

            // Boxed app
            .state('boxed', {
                url: "/boxed",
                templateUrl: "tpl/app.boxed.html"
            })

            // UI Elements 
            .state('app.ui', {
                    url: '/ui',
                    template: '<div ui-view></div>'
                })
                .state('app.ui.color', {
                    url: '/color',
                    templateUrl: 'tpl/ui_color.html'
                })
                .state('app.ui.typo', {
                    url: '/typo',
                    templateUrl: 'tpl/ui_typo.html'
                })
                .state('app.ui.icons', {
                    url: '/icons',
                    templateUrl: 'tpl/ui_icons.html',
                    controller: 'IconsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'sieve',
                                    'line-icons'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load([
                                        'assets/js/controllers/icons.js'
                                    ])
                                });
                        }]
                    }
                })
                .state('app.ui.buttons', {
                    url: '/buttons',
                    templateUrl: 'tpl/ui_buttons.html'
                })
                .state('app.ui.notifications', {
                    url: '/notifications',
                    templateUrl: 'tpl/ui_notifications.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'assets/js/controllers/notifications.js'
                            ]);
                        }]
                    }
                })
                .state('app.ui.modals', {
                    url: '/modals',
                    templateUrl: 'tpl/ui_modals.html',
                    controller: 'ModalsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'assets/js/controllers/modals.js'
                            ]);
                        }]
                    }
                })
                .state('app.ui.progress', {
                    url: '/progress',
                    templateUrl: 'tpl/ui_progress.html'
                })
                .state('app.ui.tabs', {
                    url: '/tabs',
                    templateUrl: 'tpl/ui_tabs.html',
                    resolve: { 
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'tabcollapse'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            });
                        }]
                    }
                })
                .state('app.ui.sliders', {
                    url: '/sliders',
                    templateUrl: 'tpl/ui_sliders.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'noUiSlider',
                                'ionRangeSlider'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            });
                        }]
                    }
                })
                .state('app.ui.treeview', {
                    url: '/treeview',
                    templateUrl: 'tpl/ui_treeview.html',
                    controller: 'TreeCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'navTree'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/treeview.js');
                                });
                        }]
                    }
                })
                .state('app.ui.nestables', {
                    url: '/nestables',
                    templateUrl: 'tpl/ui_nestable.html',
                    controller: 'NestableCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'nestable'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/nestable.js');
                                });
                        }]
                    }
                })

            // Form elements
            .state('app.forms', {
                    url: '/forms',
                    template: '<div ui-view></div>'
                })
                .state('app.forms.elements', {
                    url: '/elements',
                    templateUrl: 'tpl/forms_elements.html',
                    controller: 'FormElemCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'switchery',
                                    'select',
                                    'moment',
                                    'datepicker',
                                    'daterangepicker',
                                    'timepicker',
                                    'inputMask',
                                    'autonumeric',
                                    'wysihtml5',
                                    'summernote',
                                    'tagsInput',
                                    'dropzone',
                                    'typehead'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/forms_elements.js');
                                });
                        }]
                    }
                })
                .state('app.forms.layouts', {
                    url: '/layouts',
                    templateUrl: 'tpl/forms_layouts.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'datepicker',
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/forms_layouts.js');
                                });
                        }]
                    }
                })
                .state('app.forms.wizard', {
                    url: '/wizard',
                    templateUrl: 'tpl/forms_wizard.html',
                    controller: 'FormWizardCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'wizard'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/forms_wizard.js');
                                });
                        }]
                    }
                })

            // Portlets
            .state('app.portlets', {
                url: '/portlets',
                templateUrl: 'tpl/portlets.html',
                controller: 'PortletCtrl',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            'assets/js/controllers/portlets.js'
                        ]);
                    }]
                }
            })

            // Views
            .state('app.views', {
                url: '/views',
                templateUrl: 'tpl/views.html'
            })

            // Tables
            .state('app.tables', {
                    url: '/tables',
                    template: '<div ui-view></div>'
                })
                .state('app.tables.basic', {
                    url: '/basic',
                    templateUrl: 'tpl/tables_basic.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'dataTables'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/tables.js');
                                });
                        }]
                    }
                })
                .state('app.tables.dataTables', {
                    url: '/dataTables',
                    templateUrl: 'tpl/tables_dataTables.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'dataTables',
                                    'ui-grid' 
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/dataTables.js');
                                });
                        }]
                    }
                })

            // Maps
            .state('app.maps', {
                    url: '/maps',
                    template: '<div class="full-height full-width" ui-view></div>'
                })
                .state('app.maps.google', {
                    url: '/google',
                    templateUrl: 'tpl/maps_google_map.html',
                    controller: 'GoogleMapCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'google-map'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/google_map.js')
                                        .then(function() {
                                            return loadGoogleMaps();
                                        });
                                });
                        }]
                    }
                })
                .state('app.maps.vector', {
                    url: '/vector',
                    templateUrl: 'tpl/maps_vector_map.html',
                    controller: 'VectorMapCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'mapplic',
                                    'select'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/vector_map.js');
                                });
                        }]
                    }
                })

            // Charts
            .state('app.charts', {
                url: '/charts',
                templateUrl: 'tpl/charts.html',
                controller: 'ChartsCtrl',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                                'nvd3',
                                'rickshaw',
                                'sparkline'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load('assets/js/controllers/charts.js');
                            });
                    }]
                }
            })

            // Extras
            .state('app.extra', {
                    url: '/extra',
                    template: '<div ui-view></div>'
                })
                .state('app.extra.invoice', {
                    url: '/invoice',
                    templateUrl: 'tpl/extra_invoice.html'
                })
                .state('app.extra.blank', {
                    url: '/blank',
                    templateUrl: 'tpl/extra_blank.html'
                })
                .state('app.extra.gallery', {
                    url: '/gallery',
                    templateUrl: 'tpl/extra_gallery.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'isotope',
                                    'codropsDialogFx',
                                    'metrojs',
                                    'owlCarousel',
                                    'noUiSlider'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/gallery.js');
                                });
                        }]
                    }
                })
                .state('app.extra.timeline', {
                    url: '/timeline',
                    templateUrl: 'tpl/extra_timeline.html'
                })

            // Extra - Others
            .state('access', {
                    url: '/access',
                    data: {
                        requireLogin: false
                    },
                    template: '<div class="full-height" ui-view></div>'
                })
                .state('access.confirmarCadastro',{
                    url: "/confirmarCadastro/:idUsuario/:emailHash",
                    templateUrl: "tpl/confirmar_cadastro.html",
                    data: {
                        requireLogin: false
                    },
                })
                .state('access.404', {
                    url: '/404',
                    templateUrl: 'tpl/404.html'
                })
                .state('access.500', {
                    url: '/500',
                    templateUrl: 'tpl/500.html'
                })
                .state('access.login', {
                    url: '/login',
                    data: {
                        requireLogin: false
                    },
                    controller: 'LoginCtrl',
                    templateUrl: 'tpl/login.html'
                })
                .state('access.register', {
                    url: '/register',
                    controller: 'RegisterCtrl',
                    templateUrl: 'tpl/register.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'datepicker'
                            ], {
                                 insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load('assets/js/controllers/register.js');
                            });
                        }]
                    }
                })
                .state('access.lock_screen', {
                    url: '/lock_screen',
                    templateUrl: 'tpl/extra_lock_screen.html'
                })
                .state('access.recuperarSenha', {
                    url: '/recuperarSenha',
                    controller: 'RecuperarSenhaCtrl',
                    templateUrl: 'tpl/recuperar_senha.html'
                })
                .state('access.redefinirSenha', {
                    url: '/redefinirSenha/:id/:codigo',
                    controller: 'RedefinirSenhaCtrl',
                    templateUrl: 'tpl/redefinir_senha.html'
                })

        }
    ]);